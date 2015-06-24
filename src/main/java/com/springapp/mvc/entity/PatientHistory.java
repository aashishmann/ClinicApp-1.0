package com.springapp.mvc.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "patient_history")
public class PatientHistory {

    private long    id;
    private Patient patient;
    private Date    dateOfVisit;
    private String  purposeOfVisit;
    private String  chiefComplaints;
    private String  mentalSymptoms;
    private String  physicalSymptoms;
    private String  investigation;
    private String  familyHistory;
    private String  pastHistory;
    private String  thermal;
    private String  desire;
    private String  aversion;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", insertable = true, updatable = true, nullable = false, unique = true)
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Column(name = "date_of_visit", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(Date dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    @Column(name = "purpose_of_visit")
    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    public void setPurposeOfVisit(String purposeOfVisit) {
        this.purposeOfVisit = purposeOfVisit;
    }

    @Column(name = "chief_complaints")
    public String getChiefComplaints() {
        return chiefComplaints;
    }

    public void setChiefComplaints(String chiefComplaints) {
        this.chiefComplaints = chiefComplaints;
    }

    @Column(name = "mental_symptoms")
    public String getMentalSymptoms() {
        return mentalSymptoms;
    }

    public void setMentalSymptoms(String mentalSymptoms) {
        this.mentalSymptoms = mentalSymptoms;
    }

    @Column(name = "physical_symptoms")
    public String getPhysicalSymptoms() {
        return physicalSymptoms;
    }

    public void setPhysicalSymptoms(String physicalSymptoms) {
        this.physicalSymptoms = physicalSymptoms;
    }

    @Column(name = "investigation")
    public String getInvestigation() {
        return investigation;
    }

    public void setInvestigation(String investigation) {
        this.investigation = investigation;
    }

    @Column(name = "family_history")
    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    @Column(name = "past_history")
    public String getPastHistory() {
        return pastHistory;
    }

    public void setPastHistory(String pastHistory) {
        this.pastHistory = pastHistory;
    }

    @Column(name = "thermal")
    public String getThermal() {
        return thermal;
    }

    public void setThermal(String thermal) {
        this.thermal = thermal;
    }

    @Column(name = "desire")
    public String getDesire() {
        return desire;
    }

    public void setDesire(String desire) {
        this.desire = desire;
    }

    @Column(name = "aversion")
    public String getAversion() {
        return aversion;
    }

    public void setAversion(String aversion) {
        this.aversion = aversion;
    }

}