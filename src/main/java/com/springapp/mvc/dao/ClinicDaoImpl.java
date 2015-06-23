package com.springapp.mvc.dao;

import java.util.ArrayList;
import java.util.List;

import com.springapp.mvc.entity.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.Patient;
import org.springframework.util.StringUtils;

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

    @Override
    public Login validateLogin(String username, String password) {
        Criteria cr = sessionFactory.getCurrentSession().createCriteria(Login.class);
        cr.add(Restrictions.like("username", username));
        cr.add(Restrictions.like("password", password));
        return (Login) cr.uniqueResult();
    }

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

        return patientList;
    }

    @Override
    public boolean deletePatient(int id) {
        try {
            Patient patient = (Patient) sessionFactory.getCurrentSession().load(Patient.class, id);
            sessionFactory.getCurrentSession().delete(patient);
            System.out.println("Patient {} {} deleted from db." + patient.getFirstname() + patient.getLastname());
            LOG.info("Patient {} {} deleted from db.", patient.getFirstname(), patient.getLastname());
            return true;
        } catch (Exception e) {
            LOG.error("An error occured while deleting Patient details : {}", e);
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
            LOG.info("Patient {} deatails updated.", patient.getFirstname());
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

}
