package com.springapp.mvc.dao;

import java.util.List;

import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.PatientHistory;
import com.springapp.mvc.entity.PatientQueue;
import com.springapp.mvc.entity.Prescription;
import com.springapp.mvc.entity.Patient;

/**
 * Created by aashish on 3/6/15.
 */
public interface IClinicDao {

    public Patient getdetails();

    public Login validateLogin(String username, String password);

    int persistPatientDetails(Patient patient);

    public List<Patient> findPatient(SearchForm search);

    public boolean deletePatient(int id);

    public Patient findPatientById(int id);

    public void updatePatientDetails(Patient patient);

    public Boolean persistPatientHistory(PatientHistory patientHistory);

    public Boolean addPrescription(Prescription prescription);

    public List<PatientQueue> getQueueInfo();

    public boolean addToQueue(PatientQueue patientQueue);

}
