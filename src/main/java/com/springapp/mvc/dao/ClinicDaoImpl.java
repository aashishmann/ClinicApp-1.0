package com.springapp.mvc.dao;

import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by aashish on 3/6/15.
 */
@Repository
public class ClinicDaoImpl implements IClinicDao {

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
        if(cr.list()==null)
            return null;
        else {
            return (Login)cr.uniqueResult();
        }
    }
}
