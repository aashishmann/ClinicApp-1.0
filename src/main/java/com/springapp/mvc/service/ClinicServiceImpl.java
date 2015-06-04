package com.springapp.mvc.service;

import com.springapp.mvc.dao.IClinicDao;
import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by aashish on 3/6/15.
 */
@Service
public class ClinicServiceImpl implements IClinicService{

    @Autowired
    IClinicDao clinicDao;

    @Transactional
    @Override
    public User getdetails() {
        return clinicDao.getdetails();
    }

    @Transactional(readOnly=true)
    @Override
    public Login validateLogin(Login login) {
        return clinicDao.validateLogin(login.getUsername(),login.getPassword());
    }




}
