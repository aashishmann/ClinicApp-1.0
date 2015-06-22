package com.springapp.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springapp.mvc.dao.IClinicDao;
import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.PatientHistory;
import com.springapp.mvc.entity.Prescription;
import com.springapp.mvc.entity.User;

/**
 * Created by aashish on 3/6/15.
 */
@Service
public class ClinicServiceImpl implements IClinicService {

    @Autowired
    IClinicDao clinicDao;

    @Transactional
    @Override
    public User getdetails() {
        return clinicDao.getdetails();
    }

    @Transactional(readOnly = true)
    @Override
    public Login validateLogin(Login login) {
        return clinicDao.validateLogin(login.getUsername(), login.getPassword());
    }

    @Transactional
    @Override
    public Boolean persistPatientDetails(User user) {
        return clinicDao.persistPatientDetails(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findPatient(SearchForm search) {
        return clinicDao.findPatient(search);
    }

    @Transactional
    @Override
    public boolean deletePatient(int id) {
        return clinicDao.deletePatient(id);
    }

    @Transactional
    @Override
    public void updatePatientDetails(User user) {
        clinicDao.updatePatientDetails(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User findPatientById(int id) {
        return clinicDao.findPatientById(id);
    }

    @Transactional
    @Override
    public Boolean persistPatientHistory(PatientHistory patientHistory) {
        return clinicDao.persistPatientHistory(patientHistory);
    }

    @Transactional
    @Override
    public Boolean addPrescription(Prescription prescription) {
        return clinicDao.addPrescription(prescription);
    }

    @Override
    public List<User> getQueueInfo() {
        return clinicDao.getQueueInfo();
    }
}
