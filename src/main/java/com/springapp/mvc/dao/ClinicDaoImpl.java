package com.springapp.mvc.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.springapp.mvc.dto.DailyReport;
import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.Patient;
import com.springapp.mvc.entity.PatientHistory;
import com.springapp.mvc.entity.PatientQueue;
import com.springapp.mvc.entity.Prescription;

/**
 * Created by aashish on 3/6/15.
 */
// With @Transactional in your @Repository Spring is able to apply transactional
// support into your repository.
@Transactional
@Repository
public class ClinicDaoImpl implements IClinicDao {
    private static final Logger LOG = LoggerFactory.getLogger(ClinicDaoImpl.class);

    @Autowired
    SessionFactory              sessionFactory;

    @Override
    public Patient getdetails() {
        Query query = sessionFactory.getCurrentSession().createQuery("from User where id=1");
        return (Patient) query.uniqueResult();

    }

    //Validating user credentials
    @Override
    public Login validateLogin(String username, String password) {
        Criteria cr = sessionFactory.getCurrentSession().createCriteria(Login.class);
        cr.add(Restrictions.like("username", username));
        cr.add(Restrictions.like("password", password));
        return (Login) cr.uniqueResult();
    }

    //Adding patient details to database
    @Override
    public int persistPatientDetails(Patient patient) {
        try {
            sessionFactory.getCurrentSession().persist(patient);
            System.out.println(patient);
            LOG.info("Data inserted into db");
            return patient.getId();
        } catch (Exception e) {
            LOG.error("Some error occured while adding data into db", e);
            return -1;
        }
    }

    //Searching patient detials against the details provided at search form
    @SuppressWarnings("unchecked")
    @Override
    public List<Patient> findPatient(SearchForm search) {
        List<Patient> patientList = new ArrayList<Patient>();

        Criteria cr = sessionFactory.getCurrentSession().createCriteria(Patient.class);

        Disjunction disjunction = Restrictions.disjunction();
        if (!StringUtils.isEmpty(search.getFirstname()))
            disjunction.add(Property.forName("firstname").eq(search.getFirstname()));
        if (!StringUtils.isEmpty(search.getLastname()))
            disjunction.add(Property.forName("lastname").eq(search.getLastname()));
        if (!StringUtils.isEmpty(search.getMobile()))
            disjunction.add(Property.forName("mobile").eq(search.getMobile()));
        if (!StringUtils.isEmpty(search.getDependent()))
            disjunction.add(Property.forName("dependent").eq(search.getDependent()));
        if (!StringUtils.isEmpty(search.getRefferedBy()))
            disjunction.add(Property.forName("refferedBy").eq(search.getRefferedBy()));
        cr.add(disjunction);

        patientList = (List<Patient>) cr.list();

        for (Patient patient : patientList) {
            System.out.println(patient);
        }
        if (patientList.size() == 0) {
            return null;
        }
        return patientList;
    }

    //Delete patient record from patient table
    @Override
    public boolean deletePatient(int id) {
        try {
            Patient patient = (Patient) sessionFactory.getCurrentSession().load(Patient.class, id);
            sessionFactory.getCurrentSession().delete(patient);
            //System.out.println("Patient deleted from db. " + patient.getFirstname() + patient.getLastname());
            LOG.info("Patient {} {} deleted from db.", patient.getFirstname(), patient.getLastname());
            return true;
        } catch (Exception e) {
            LOG.error("An error occured while deleting Patient details : ", e);
            return false;
        }
    }

    @Override
    public Patient findPatientById(int id) {
        return (Patient) sessionFactory.getCurrentSession().get(Patient.class, id);
    }

    @Override
    public void updatePatientDetails(Patient patient) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(patient);
            LOG.info("Patient {} details updated.", patient.getFirstname());
        } catch (Exception e) {
            LOG.error("An error occured while updating patient {} details : {}", patient.getFirstname(), e);
        }
    }

    @Override
    public Boolean persistPatientHistory(PatientHistory patientHistory) {
        try {
            sessionFactory.getCurrentSession().persist(patientHistory);
            System.out.println(patientHistory);
            LOG.info("Patient history inserted into db");
            return Boolean.TRUE;
        } catch (Exception e) {
            LOG.error("Some error occured while adding patient history into db", e);
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean addPrescription(Prescription prescription) {
        try {
            sessionFactory.getCurrentSession().persist(prescription);
            LOG.info("Prescription added into db.");
            return Boolean.TRUE;
        } catch (Exception e) {
            LOG.error("Some error occured while adding prescription for patient :", e);
            return Boolean.FALSE;
        }
    }

    @Override
    public List<PatientQueue> getQueueInfo() {
        System.out.println("get queue info at dao");
        List<PatientQueue> patientQueue = sessionFactory.getCurrentSession().createQuery("from PatientQueue").list();
        /*for(Patient patient : patientQueue){
            LOG.info("Person List::"+patient);
        }*/
        LOG.info("Size of Queue : {}", patientQueue.size());
        if (patientQueue.size() == 0) {
            return null;
        }
        return patientQueue;
    }

    @Override
    public boolean addToQueue(PatientQueue patientQueue) {
        try {
            sessionFactory.getCurrentSession().merge(patientQueue);
            System.out.println(patientQueue);
            LOG.info("Patient added to queue in DB.");
            System.out.println("Patient added to queue in DB.");
            return true;
        } catch (Exception e) {
            LOG.error("Some error occured while adding patient to queue in db", e);
            System.out.println("Some error occured while adding patient to queue in db");
            return false;
        }
    }

    @Override
    public void updatePatientHistory(PatientHistory patientHistory) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(patientHistory);
            LOG.info("Patient history updated for patient ID {}.", patientHistory.getPatient().getId());
        } catch (Exception e) {
            LOG.error("An error occured while updating patient history details for patient ID {} : {}", patientHistory.getPatient().getId(), e);
        }
    }

    @Override
    public void updatePrescription(Prescription prescription) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(prescription);
            LOG.info("Prescription updated for patient ID {}.", prescription.getPatient().getId());
        } catch (Exception e) {
            LOG.error("An error occured while updating prescription for patient ID {} : {}", prescription.getPatient().getId(), e);
        }
    }

    @Override
    public List<Prescription> getLatestPrescription(List<Integer> patientIds) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "select pres from Prescription pres join fetch pres.patient where pres.patient.id in (:patientIds) AND pres.entryTime = current_date() ");
        query.setParameterList("patientIds", patientIds);
        List<Prescription> prescriptions = query.list();
        return prescriptions;
    }

    @Override
    public boolean deleteFromQueue(int id) {
        try {
            PatientQueue patientQueue = (PatientQueue) sessionFactory.getCurrentSession().load(PatientQueue.class, id);
            sessionFactory.getCurrentSession().delete(patientQueue);
            LOG.info("Patient {} {} deleted from queue.", patientQueue.getPatient().getFirstname(), patientQueue.getPatient().getLastname());
            return true;
        } catch (Exception e) {
            LOG.error("An error occured while deleting Patient Queue : ", e);
            return false;
        }
    }

    @Override
    public PatientHistory getPatientHistory(int patientId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PatientHistory.class);
        criteria.add(Restrictions.eq("patient.id", patientId));
        return (PatientHistory) criteria.uniqueResult();
    }

    @Override
    public List<Prescription> getFiveLatestPrescriptions(int patientId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Prescription.class);
        criteria.add(Restrictions.eq("patient.id", patientId));
        criteria.addOrder(Order.desc("entryTime"));
        criteria.setMaxResults(5);

        List<Prescription> prescriptions = criteria.list();
        return prescriptions;
    }

    //Daily Report Generation logic
    @Override
    public List<DailyReport> generateDailyReport() {
        Query query = sessionFactory.getCurrentSession().createQuery("");
        List<DailyReport> dailyReport = query.list();
        System.out.println(dailyReport);
        //select firstname,lastname,charges from patient p1,prescription p2 where p1.id=p2.patient_id and p2.entry_time = CURRENT_DATE();
        return null;
    }

}
