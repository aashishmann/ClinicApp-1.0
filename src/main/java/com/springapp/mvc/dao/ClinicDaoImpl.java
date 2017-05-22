package com.springapp.mvc.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.springapp.mvc.entity.*;
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

import com.springapp.mvc.dto.SearchForm;

/**
 * Created by aashish on 3/6/15.
 */
// With @Transactional in your @Repository Spring is able to apply transactional
// support into your repository.
@Transactional
@Repository
public class ClinicDaoImpl implements IClinicDao {
    private static final Logger LOG       = LoggerFactory.getLogger(ClinicDaoImpl.class);

    @Autowired
    SessionFactory              sessionFactory;

    public static int           queueSize = 20;

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
            disjunction.add(Property.forName("firstname").like(search.getFirstname()));
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

    @SuppressWarnings("unchecked")
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

    //Get list of patients in queue
    @SuppressWarnings("unchecked")
    @Override
    public List<PatientQueue> getQueueInfo() {
        System.out.println("get queue info at dao");
        Query query = sessionFactory.getCurrentSession().createQuery("from PatientQueue order by id desc");
        query.setMaxResults(queueSize);
        List<PatientQueue> patientQueue = query.list();
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

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
    @Override
    public PatientHistory getPatientHistory(int patientId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PatientHistory.class);
        criteria.add(Restrictions.eq("patient.id", patientId));
        return (PatientHistory) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Prescription> getFiveLatestPrescriptions(int patientId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Prescription.class);
        criteria.add(Restrictions.eq("patient.id", patientId));
        criteria.addOrder(Order.desc("entryTime"));
        criteria.setMaxResults(5);

        List<Prescription> prescriptions = criteria.list();
        if (prescriptions.isEmpty())
            return new ArrayList<>();
        return prescriptions;
    }

    //Daily Report Generation logic
    @SuppressWarnings("unchecked")
    @Override
    public List<Prescription> generateDailyReport() {
        Query query = sessionFactory.getCurrentSession().createQuery("select pres from Prescription pres where pres.entryTime = current_date() ");
        List<Prescription> dailyReportList = query.list();
        return dailyReportList;
    }

    //Add new user to database
    @Override
    public boolean addUserLogin(Login login) {
        try {
            sessionFactory.getCurrentSession().persist(login);
            System.out.println("Added to db :" + login);
            LOG.info("New user inserted into db");
            return true;
        } catch (Exception e) {
            LOG.error("Some error occured while adding new user into db", e);
            return false;
        }
    }

    //Get all user credentials
    @SuppressWarnings("unchecked")
    @Override
    public List<Login> getAllUsers() {
        System.out.println("get users from dao");
        List<Login> loginList = sessionFactory.getCurrentSession().createQuery("from Login").list();
        //System.out.println(loginList);
        if (loginList.size() == 0) {
            return null;
        }
        return loginList;
    }

    //Delete login details of a user
    @Override
    public boolean deleteUser(int id) {
        try {
            Login login = (Login) sessionFactory.getCurrentSession().load(Login.class, id);
            sessionFactory.getCurrentSession().delete(login);
            System.out.println("User deleted from db. " + login.getUsername() + login.getPassword());
            return true;
        } catch (Exception e) {
            LOG.error("An error occured while deleting User details: ", e);
            return false;
        }
    }

    //get details of the user by id
    @SuppressWarnings("unchecked")
    @Override
    public Login getUserById(int id) {
        return (Login) sessionFactory.getCurrentSession().get(Login.class, id);
    }

    //updating login details of user
    @Override
    public boolean updateUserDetails(Login login) {
        Login loginDetails = (Login) sessionFactory.getCurrentSession().get(Login.class, login.getId());
        if (loginDetails != null) {
            loginDetails.setUsername(login.getUsername());
            loginDetails.setPassword(login.getPassword());
            loginDetails.setRoleType(login.getRoleType());
            System.out.println("Updating at dao");
            sessionFactory.getCurrentSession().update(loginDetails);
            System.out.println("update complete");
            return true;
        }
        System.out.println(login);
        System.out.println("no login details found" + loginDetails);
        return false;
    }

    //save updated details of user
    @Override
    public boolean savePatientDetails(Patient existingPatient) {
        Patient patient = (Patient) sessionFactory.getCurrentSession().get(Patient.class, existingPatient.getId());
        if (patient != null) {
            System.out.println("updating details");
            sessionFactory.getCurrentSession().update(existingPatient);
            System.out.println("details updated successfully");
            return true;
        }
        System.out.println("unable to update some error occured");
        return false;
    }

    @Override
    public void deleteFromQueue() {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from PatientQueue where entryTime < current_date");
        query.executeUpdate();
    }

    // Getting prescription information for month and year specified to generate monthly report.
    @Override
    public List<Prescription> generateMonthlyReport(List<Date> dates) {
        Query query = sessionFactory.getCurrentSession().createQuery("select pres from Prescription pres where pres.entryTime between :startDate and :endDate");
        query.setParameter("startDate", dates.get(0));
        query.setParameter("endDate", dates.get(1));
        List<Prescription> prescriptions = query.list();
        return prescriptions;
    }

    @Override
    public List<String> getAllAmountLabels() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("select amt_code from amount");
        List<String> labels = query.list();
        return labels;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Charges findChargesByCode(String code) {
        Query query = sessionFactory.getCurrentSession().createQuery("select ch from Charges ch where ch.code = :code");
        query.setParameter("code",code);
        return (Charges) query.uniqueResult();
        //return (Charges) sessionFactory.getCurrentSession().get (Charges.class, code);
    }

}
