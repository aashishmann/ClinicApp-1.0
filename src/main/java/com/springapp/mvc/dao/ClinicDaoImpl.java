package com.springapp.mvc.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.PatientHistory;
import com.springapp.mvc.entity.PatientQueue;
import com.springapp.mvc.entity.Prescription;
import com.springapp.mvc.entity.User;
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
    public User getdetails() {
        Query query = sessionFactory.getCurrentSession().createQuery("from User where id=1");
        return (User) query.uniqueResult();

    }

    @Override
    public Login validateLogin(String username, String password) {
        Criteria cr = sessionFactory.getCurrentSession().createCriteria(Login.class);
        cr.add(Restrictions.like("username", username));
        cr.add(Restrictions.like("password", password));
        return (Login) cr.uniqueResult();
    }

    @Override
    public int persistPatientDetails(User user) {
        try {
            sessionFactory.getCurrentSession().persist(user);
            System.out.println(user);
            LOG.info("Data inserted into db");
            return user.getId();
        } catch (Exception e) {
            LOG.error("Some error occured while adding data into db", e);
            return -1;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findPatient(SearchForm search) {
        List<User> patientList = new ArrayList<User>();

        Criteria cr = sessionFactory.getCurrentSession().createCriteria(User.class);

        Disjunction disjunction = Restrictions.disjunction();
        if(!StringUtils.isEmpty(search.getFirstname()))
            disjunction.add(Property.forName("firstname").eq(search.getFirstname()));
        if(!StringUtils.isEmpty(search.getLastname()))
            disjunction.add(Property.forName("lastname").eq(search.getLastname()));
        if(!StringUtils.isEmpty(search.getMobile()))
            disjunction.add(Property.forName("mobile").eq(search.getMobile()));
        if(!StringUtils.isEmpty(search.getDependent()))
            disjunction.add(Property.forName("dependent").eq(search.getDependent()));
        if(!StringUtils.isEmpty(search.getRefferedBy()))
            disjunction.add(Property.forName("refferedBy").eq(search.getRefferedBy()));
        cr.add(disjunction);

        patientList = (List<User>) cr.list();

        for (User user : patientList) {
            System.out.println(user);
        }

        return patientList;
    }

    @Override
    public boolean deletePatient(int id) {
        try {
            User user = (User) sessionFactory.getCurrentSession().load(User.class, id);
            sessionFactory.getCurrentSession().delete(user);
            System.out.println("Patient {} {} deleted from db." + user.getFirstname() + user.getLastname());
            LOG.info("Patient {} {} deleted from db.", user.getFirstname(), user.getLastname());
            return true;
        } catch (Exception e) {
            LOG.error("An error occured while deleting Patient details : {}", e);
            return false;
        }
    }

    @Override
    public User findPatientById(int id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public void updatePatientDetails(User user) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(user);
            LOG.info("Patient {} deatails updated.", user.getFirstname());
        } catch (Exception e) {
            LOG.error("An error occured while updating patient {} details : {}", user.getFirstname(), e);
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
        /*for(User patient : patientQueue){
            LOG.info("Person List::"+patient);
        }*/
        return patientQueue;
    }

    @Override
    public boolean addToQueue(PatientQueue patientQueue) {
        try {
            sessionFactory.getCurrentSession().persist(patientQueue);
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
