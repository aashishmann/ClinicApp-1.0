package com.springapp.mvc.dao;

import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.User;

import java.util.List;

/**
 * Created by aashish on 3/6/15.
 */
public interface IClinicDao {

    public User getdetails();

    public Login validateLogin(String username,String password);

    Boolean persistPatientDetails(User user);

    public List<User> findPatient(SearchForm search);

}
