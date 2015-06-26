package com.springapp.mvc.dto;

public class DailyReport {
    private String firstname;
    private String lastname;
    private int    charges;

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

    @Override
    public String toString() {
        return "DailyReport [firstname=" + firstname + ", lastname=" + lastname + ", charges=" + charges + "]";
    }

}
