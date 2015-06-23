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
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "prescription")
public class Prescription {

    private long           id;
    private String         medicines;
    private Date           entryTime;
    private int            charges;
    private String         followupRemark;
    private Date           revisitDate;
    private PatientHistory patientHistory;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "medicines")
    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    @Column(name = "entry_time")
    @PrePersist
    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    @Column(name = "charges")
    public int getCharges() {
        return charges;
    }

    public void setCharges(int charges) {
        this.charges = charges;
    }

    @Column(name = "followup_remark")
    public String getFollowupRemark() {
        return followupRemark;
    }

    public void setFollowupRemark(String followupRemark) {
        this.followupRemark = followupRemark;
    }

    @Column(name = "revisit_date")
    public Date getRevisitDate() {
        return revisitDate;
    }

    public void setRevisitDate(Date revisitDate) {
        this.revisitDate = revisitDate;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = true, updatable = true, nullable = false, unique = true)
    public PatientHistory getPatientHistory() {
        return patientHistory;
    }

    public void setPatientHistory(PatientHistory patientHistory) {
        this.patientHistory = patientHistory;
    }

}
