package com.springapp.mvc.entity.OldDB;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by aashish on 18/6/16.
 */
@Entity
@Table(name="old_med_details")
public class OldMedicalDetails {
    int     id;
    int patientCardNumber;
    Date medicineDate;
    String medicine;
    String amountCode;
    String note;
    String medicine1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "patient_card_no")
    public int getPatientCardNumber() {
        return patientCardNumber;
    }

    public void setPatientCardNumber(int patientCardNumber) {
        this.patientCardNumber = patientCardNumber;
    }

    @Column(name = "medicine_date")
    @Temporal(TemporalType.DATE)
    public Date getMedicineDate() {
        return medicineDate;
    }

    public void setMedicineDate(Date medicineDate) {
        this.medicineDate = medicineDate;
    }

    @Column(name="medicine")
    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    @Column(name="amountCode")
    public String getAmountCode() {
        return amountCode;
    }

    public void setAmountCode(String amountCode) {
        this.amountCode = amountCode;
    }

    @Column(name="note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name="medicine1")
    public String getMedicine1() {
        return medicine1;
    }

    public void setMedicine1(String medicine1) {
        this.medicine1 = medicine1;
    }
}
