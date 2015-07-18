package com.springapp.mvc.dto;

import java.util.List;

import com.springapp.mvc.entity.Patient;
import com.springapp.mvc.entity.PatientHistory;
import com.springapp.mvc.entity.Prescription;

public class PatientProfileDTO {

    private Patient            patient;
    private PatientHistory     patientHistory;
    private List<Prescription> prescriptionList;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public PatientHistory getPatientHistory() {
        return patientHistory;
    }

    public void setPatientHistory(PatientHistory patientHistory) {
        this.patientHistory = patientHistory;
    }

    public List<Prescription> getPrescriptionList() {
        return prescriptionList;
    }

    public void setPrescriptionList(List<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
    }

    @Override
    public String toString() {
        return "PatientProfileDTO [patient=" + patient + ", patientHistory=" + patientHistory + ", prescriptionList=" + prescriptionList + "]";
    }

}
