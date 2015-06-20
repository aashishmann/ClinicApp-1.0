package com.springapp.mvc.dto;

public class SearchForm {

    private String firstname;
    private String lastname;
    private String mobile;
    private String dependent;
    private String refferedBy;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDependent() {
        return dependent;
    }

    public void setDependent(String dependent) {
        this.dependent = dependent;
    }

    public String getRefferedBy() {
        return refferedBy;
    }

    public void setRefferedBy(String refferedBy) {
        this.refferedBy = refferedBy;
    }

    @Override
    public String toString() {
        return "SearchForm [firstname=" + firstname + ", lastname=" + lastname + ", mobile=" + mobile + ", dependent=" + dependent + ", refferedBy=" + refferedBy + "]";
    }
}
