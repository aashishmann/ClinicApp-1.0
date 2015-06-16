package com.springapp.mvc.dao;

import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.User;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aashish on 3/6/15.
 */
@Repository
public class ClinicDaoImpl implements IClinicDao {
    private static final Logger LOG = LoggerFactory.getLogger(ClinicDaoImpl.class);

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public User getdetails() {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "from User where id=1");
        return (User) query.uniqueResult();

    }

    @Override
    public Login validateLogin(String username, String password) {
        Criteria cr = sessionFactory.getCurrentSession().createCriteria(Login.class);
        cr.add(Restrictions.like("username", username));
        cr.add(Restrictions.like("password", password));
        return (Login)cr.uniqueResult();
    }

    @Override
    public Boolean persistPatientDetails(User user) {
        try {
            sessionFactory.getCurrentSession().persist(user);
            System.out.println(user);
            LOG.info("Data inserted into db");
            return Boolean.TRUE;
        } catch (Exception e) {
            LOG.error("Some error occured while adding data into db",e);
            return Boolean.FALSE;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findPatient(SearchForm search) {
        List<User> patientList=new ArrayList<User>();

        Criteria cr = sessionFactory.getCurrentSession().createCriteria(User.class);

        Disjunction orExpression = Restrictions.disjunction();

        orExpression.add(Property.forName("firstname").eq(search.getFirstname()))
                .add(Property.forName("lastname").eq(search.getLastname()))
                .add(Property.forName("mobile").eq(search.getMobile()))
                .add(Property.forName("dependent").eq(search.getDependent()))
                .add(Property.forName("refferedBy").eq(search.getRefferedBy()));

        cr.add(orExpression);

        patientList=(List<User>)cr.list();

        for(User user:patientList){
            System.out.println(user);
        }

        return patientList;
    }

	@Override
	public void deletePatient(int id) {
		try {
            User user = (User) sessionFactory.getCurrentSession().load(User.class, id);
            sessionFactory.getCurrentSession().delete(user);
            System.out.println("Patient {} {} deleted from db." + user.getFirstname() + user.getLastname());
            LOG.info("Patient {} {} deleted from db." , user.getFirstname() , user.getLastname());
        } catch (Exception e) {
            LOG.error("An error occured while deleting Patient details : {}", e);
        }
	}

	@Override
	public User findPatientById(int id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("id", id));
		return (User) criteria.uniqueResult();
	}
}
