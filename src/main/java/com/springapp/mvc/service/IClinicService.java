package com.springapp.mvc.service;

import java.util.List;

import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.PatientHistory;
import com.springapp.mvc.entity.PatientQueue;
import com.springapp.mvc.entity.Prescription;
import com.springapp.mvc.entity.User;

/**
 * Created by aashish on 3/6/15.
 */
public interface IClinicService {

    public User getdetails();

    public Login validateLogin(Login login);

    int persistPatientDetails(User user);

    List<User> findPatient(SearchForm search);

    public boolean deletePatient(int id);

    public void updatePatientDetails(User user);

    public User findPatientById(int id);

    public Boolean persistPatientHistory(PatientHistory patientHistory);

    public Boolean addPrescription(Prescription prescription);

    public List<PatientQueue> getQueueInfo();

    public boolean addToQueue(PatientQueue patientQueue);
}
