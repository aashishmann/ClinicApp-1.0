package com.springapp.mvc.service;

import java.util.List;

import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.User;

/**
 * Created by aashish on 3/6/15.
 */
public interface IClinicService {

	public User getdetails();

	public Login validateLogin(Login login);

	Boolean persistPatientDetails(User user);

	List<User> findPatient(SearchForm search);

	public void deletePatient(int id);

	public void updatePatientDetails(User user);

}
