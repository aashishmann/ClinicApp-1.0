package com.springapp.mvc.dto;

import java.sql.Date;

public class PrescriptionDTO {
    private String medicines;
    private Date   entryTime;
    private String charges;
    private String followupRemark;
    private Date   revisitDate;
    private int    patientId;

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getFollowupRemark() {
        return followupRemark;
    }

    public void setFollowupRemark(String followupRemark) {
        this.followupRemark = followupRemark;
    }

    public Date getRevisitDate() {
        return revisitDate;
    }

    public void setRevisitDate(Date revisitDate) {
        this.revisitDate = revisitDate;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    @Override
    public String toString() {
        return "PrescriptionDTO [medicines=" + medicines + ", entryTime=" + entryTime + ", charges=" + charges + ", followupRemark=" + followupRemark + ", revisitDate="
                + revisitDate + ", patientId=" + patientId + "]";
    }

}
