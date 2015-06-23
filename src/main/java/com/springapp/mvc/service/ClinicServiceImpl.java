package com.springapp.mvc.service;

import java.util.List;

import com.springapp.mvc.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springapp.mvc.dao.IClinicDao;
import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.Patient;

/**
 * Created by aashish on 3/6/15.
 */
@Service
public class ClinicServiceImpl implements IClinicService {

    @Autowired
    IClinicDao clinicDao;

    @Transactional
    @Override
    public Patient getdetails() {
        return clinicDao.getdetails();
    }

    @Transactional(readOnly = true)
    @Override
    public Login validateLogin(Login login) {
        return clinicDao.validateLogin(login.getUsername(), login.getPassword());
    }

    @Transactional
    @Override
    public int persistPatientDetails(Patient patient) {
        int id = clinicDao.persistPatientDetails(patient);
        if(id<0){
            System.out.println("Unable to persist patient details in patient table");
            return id;
        }


        PatientQueue patientQueue = new PatientQueue();
        patientQueue.setPatient(clinicDao.findPatientById(id));
        /*patientQueue.setPatientId(id);
        patientQueue.setFirstname(patient.getFirstname());
        patientQueue.setLastname(patient.getLastname());*/
        if(addToQueue(patientQueue)){
            System.out.println("Patient details added to queue");
            return id;
        }
        System.out.println("Some error occured while adding patient details to queue");
        return -1;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Patient> findPatient(SearchForm search) {
        return clinicDao.findPatient(search);
    }

    @Transactional
    @Override
    public boolean deletePatient(int id) {
        return clinicDao.deletePatient(id);
    }

    @Transactional
    @Override
    public void updatePatientDetails(Patient patient) {
        clinicDao.updatePatientDetails(patient);
    }

    @Transactional(readOnly = true)
    @Override
    public Patient findPatientById(int id) {
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
    public List<PatientQueue> getQueueInfo() {
        return clinicDao.getQueueInfo();
    }

    @Override
    public boolean addToQueue(PatientQueue patientQueue) {
        return clinicDao.addToQueue(patientQueue);
    }
}
