package com.springapp.mvc.utils;

import com.springapp.mvc.entity.Charges;
import com.springapp.mvc.entity.OldDB.OldMedicalDetails;
import com.springapp.mvc.entity.OldDB.OldPatient;
import com.springapp.mvc.entity.Patient;
import com.springapp.mvc.entity.PatientHistory;
import com.springapp.mvc.entity.Prescription;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by aashish on 21/11/16.
 */
public class DataMigration {
    private static SessionFactory sessionFactory = buildSessionFactory();
    //public static DateFormat readFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
    //public static DateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);

    public static DateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    public static DateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);

    private static SessionFactory buildSessionFactory()
    {

        Configuration cfg = new Configuration()
                //.addClass(Patient.class)
                .addAnnotatedClass(Patient.class)
                .addAnnotatedClass(PatientHistory.class)
                .addAnnotatedClass(Prescription.class)
                .addAnnotatedClass(OldMedicalDetails.class)
                .addAnnotatedClass(OldPatient.class)
                .addAnnotatedClass(Charges.class)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.username", "root")
                .setProperty("hibernate.connection.password", "root")
                .setProperty("hibernate.connection.url", "jdbc:mysql://localhost/snap_temp?rewriteBatchedStatement=true&autoReconnect=true")
                .setProperty("hibernate.show_sql", "false")
                .setProperty("hibernate.format_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto","none");
        return cfg.buildSessionFactory();
    }

    public static void main(String []args) {

        sampleRead();

    }

    public static Query paginatedQuery(Session session, int offset, int pageSize) {
        Query q1 = session.createSQLQuery("select op.patient_card_no from old_patient op");
        q1.setFirstResult(offset);
        q1.setMaxResults(pageSize);

        List<String> cardNumbers = q1.list();
        if(!cardNumbers.isEmpty()) {
        System.out.println(cardNumbers.size() + " Patient card numbers fetched");
        System.out.println("offSet " + offset + " pageSize " + pageSize);
        System.out.println("Numbers " + Arrays.toString(cardNumbers.toArray()));
        Query q = session.createSQLQuery("select op.id, op.patient_card_no, op.name, op.address, op.age, op.sex, op.problem, op.diagnosis, omd.medicine_date, omd.medicine, omd.amt_code, omd.note, omd.medicine1 from old_patient op join old_med_details omd ON op.patient_card_no=omd.patient_card_no where op.patient_card_no in (:cardNumbers) order by op.patient_card_no");
        q.setParameterList("cardNumbers",cardNumbers);
        return q;
        }
        else {
            return null;
        }

    }

    public static Charges getCharges(Session session, String code) {
        Query q1 = session.createQuery("select ch from Charges ch where ch.code=:code");
        q1.setParameter("code",code);
        Charges  charges = (Charges)q1.uniqueResult();
        return charges;
    }

    public static void sampleRead() {
        Session session = null;

        Transaction transaction = null;
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            /*Query q = session.createQuery("select op from OldPatient op where op.id = :id");
            q.setParameter("id",3569);
            OldPatient op = (OldPatient)q.uniqueResult();
            System.out.println(op.toString());*/

            int offset = 0;
            int pageSize = 500;
            int prevPatientId = 0;

            Query q1 = paginatedQuery(session, offset, pageSize);
            List<Object []> result = q1.list();
            System.out.println("Fetched " + result.size() + " rows");
            int i=0;
            String chargesCode = null;
            while(result != null) {
                Patient patient = null;
                for (Object[] row : result) {
                    Prescription prescription = new Prescription();
                    int patientId = (int)row[0];
                    if (prevPatientId != patientId) {

                        patient = new Patient();
                        PatientHistory patientHistory = new PatientHistory();
                        patient.setFirstname((String) row[2]);
                        patient.setAddress((String) row[3]);
                        patient.setAge((int) row[4]);
                        patient.setSex((String) row[5]);

                        patientHistory.setInvestigation((String) row[7]);
                        patientHistory.setChiefComplaints((String) row[6]);
                        patientHistory.setPatient(patient);
                        patientHistory.setDateOfVisit((java.sql.Timestamp) row[8]);
                        session.save(patientHistory);

                        // set prev Patient Id as current patient ID to exit from this loop next time
                        prevPatientId = patientId;
                        System.out.println("Saving " + (i++) +" " + patient.getFirstname() + " " + patient.getId() + " " + row[1]);

                    }
                    prescription.setEntryTime((java.sql.Timestamp) row[8]);
                    prescription.setMedicines((String) row[9]);
                    prescription.setFollowupRemark((String) row[11]);
                    chargesCode = (String)row[10];
                    if(!StringUtils.isEmpty(chargesCode)) {
                        prescription.setCharges(getCharges(session, chargesCode));
                    }
                    Patient p = new Patient();
                    p.setId((int) row[0]);
                    prescription.setPatient(patient);
                    session.save(prescription);
                }

                // paginated query
                offset = offset + pageSize;
                q1 = paginatedQuery(session, offset, pageSize);
                if(q1 == null) {
                    break;
                }
                result = q1.list();
                System.out.println("Fetched " + result.size() + " rows");
            }
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                shutdown();
            }
        }
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    public static void shutdown()
    {
        getSessionFactory().close();
    }
}
