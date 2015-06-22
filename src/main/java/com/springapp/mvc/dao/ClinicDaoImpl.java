package com.springapp.mvc.dao;

import java.util.ArrayList;
import java.util.List;

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
import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.PatientHistory;
import com.springapp.mvc.entity.Prescription;
import com.springapp.mvc.entity.User;

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
    public Boolean persistPatientDetails(User user) {
        try {
            sessionFactory.getCurrentSession().persist(user);
            System.out.println(user);
            LOG.info("Data inserted into db");
            return Boolean.TRUE;
        } catch (Exception e) {
            LOG.error("Some error occured while adding data into db", e);
            return Boolean.FALSE;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findPatient(SearchForm search) {
        List<User> patientList = new ArrayList<User>();

        Criteria cr = sessionFactory.getCurrentSession().createCriteria(User.class);

        Disjunction orExpression = Restrictions.disjunction();

        orExpression.add(Property.forName("firstname").eq(search.getFirstname())).add(Property.forName("lastname").eq(search.getLastname())).add(
                Property.forName("mobile").eq(search.getMobile())).add(Property.forName("dependent").eq(search.getDependent())).add(
                Property.forName("refferedBy").eq(search.getRefferedBy()));

        cr.add(orExpression);

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

}
