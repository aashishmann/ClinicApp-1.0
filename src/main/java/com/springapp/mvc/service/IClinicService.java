package com.springapp.mvc.service;

import java.util.List;

import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.*;
import com.springapp.mvc.entity.Patient;

/**
 * Created by aashish on 3/6/15.
 */
public interface IClinicService {

    public Patient getdetails();

    public Login validateLogin(Login login);

    int persistPatientDetails(Patient patient);

    List<Patient> findPatient(SearchForm search);

    public boolean deletePatient(int id);

    public void updatePatientDetails(Patient patient);

    public Patient findPatientById(int id);

    public Boolean persistPatientHistory(PatientHistory patientHistory);

    public Boolean addPrescription(Prescription prescription);

    public List<PatientQueue> getQueueInfo();

    public boolean addToQueue(PatientQueue patientQueue);

    public int savePatientAndAddToQueue(Patient patient);
}
