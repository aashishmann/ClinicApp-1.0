package com.springapp.mvc.service;

import java.util.List;

import com.springapp.mvc.dto.DailyReport;
import com.springapp.mvc.dto.LoginForm;
import com.springapp.mvc.dto.Medicine;
import com.springapp.mvc.dto.MonthlyReport;
import com.springapp.mvc.dto.PrescriptionDTO;
import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.*;

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

    public List<PatientQueue> getQueueInfo();

    public int savePatientAndAddToQueue(Patient patient);

    public void updatePatientHistory(PatientHistory patientHistory);

    public void updatePrescription(Prescription prescription);

    public List<Medicine> getLatestPrescription();

    public boolean deleteFromQueue(int id);

    public boolean addPatientToQueue(int patientId);

    public PatientHistory getPatientHistory(int patientId);

    public List<PrescriptionDTO> getFiveLatestPrescriptions(int patientId);

    public List<DailyReport> generateDailyReport();

    public boolean addUserLogin(Login login);

    public List<Login> getAllUsers();

    public boolean deleteUser(int id);

    public Login getUserById(int id);

    public boolean updateUserDetails(LoginForm loginForm);

    public boolean savePatientDetails(Patient patient);

    public boolean addPrescription(PrescriptionDTO prescriptionDTO);

    public void deleteFromQueue();

    public List<MonthlyReport> generateMonthlyReport(int month,int year);

    public List<String> getAllAmountLabels();

    public Charges findChargesByCode(String code);
}
