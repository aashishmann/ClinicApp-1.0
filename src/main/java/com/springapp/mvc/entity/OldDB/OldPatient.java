package com.springapp.mvc.entity.OldDB;

import javax.persistence.*;

/**
 * Created by aashish on 14/9/16.
 */
@Entity
@Table(name = "old_patient")
public class OldPatient {
    int id;
    int patientCardNumber;
    String name;
    String address;
    int age;
    String sex;
    String problem;
    String diagnosis;

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

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name="age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Column(name="sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name="problem")
    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    @Column(name="diagnosis")
    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public String toString() {
        return "OldPatient{" +
                "id=" + id +
                ", patientCardNumber=" + patientCardNumber +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", problem='" + problem + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                '}';
    }
}
