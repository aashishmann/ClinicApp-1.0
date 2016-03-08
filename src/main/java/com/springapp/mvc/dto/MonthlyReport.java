package com.springapp.mvc.dto;

import java.util.Date;

public class MonthlyReport {

    private String firstname;
    private String lastname;
    private int    charges;
    private Date   entryDate;

    public MonthlyReport() {
    }

    public MonthlyReport(String firstname, String lastname, int charges, Date entryDate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.charges = charges;
        this.entryDate = entryDate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getCharges() {
        return charges;
    }

    public void setCharges(int charges) {
        this.charges = charges;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MonthlyReport [firstname=").append(firstname).append(", lastname=").append(lastname).append(", charges=").append(charges).append(", entryDate=").append(
                entryDate).append("]");
        return builder.toString();
    }

}
