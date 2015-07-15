package com.springapp.mvc.dao;

import java.util.List;

import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.Patient;
import com.springapp.mvc.entity.PatientHistory;
import com.springapp.mvc.entity.PatientQueue;
import com.springapp.mvc.entity.Prescription;

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

    public void updatePatientHistory(PatientHistory patientHistory);

    public void updatePrescription(Prescription prescription);

    public List<Prescription> getLatestPrescription(List<Integer> patientIds);

    public boolean deleteFromQueue(int id);

    public PatientHistory getPatientHistory(int patientId);

    public List<Prescription> getFiveLatestPrescriptions(int patientId);

    public List<Prescription> generateDailyReport();

    public boolean addUserLogin(Login login);

    public List<Login> getAllUsers();

    public boolean deleteUser(int id);

    public Login getUserById(int id);

    public boolean updateUserDetails(Login login);

    public boolean savePatientDetails(Patient existingPatient);

    public void deleteFromQueue();

}
