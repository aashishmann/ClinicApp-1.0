package com.springapp.mvc.dao;

import java.util.List;

import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.User;

/**
 * Created by aashish on 3/6/15.
 */
public interface IClinicDao {

    public User getdetails();

    public Login validateLogin(String username, String password);

    Boolean persistPatientDetails(User user);

    public List<User> findPatient(SearchForm search);

    public boolean deletePatient(int id);

    public User findPatientById(int id);

    public void updatePatientDetails(User user);

}
