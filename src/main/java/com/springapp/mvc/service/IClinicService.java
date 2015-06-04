package com.springapp.mvc.service;

import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.User;

/**
 * Created by aashish on 3/6/15.
 */
public interface IClinicService {

    public User getdetails();

    public Login validateLogin(Login login);

}
