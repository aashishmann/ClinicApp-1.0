package com.springapp.mvc.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by aashish on 7/2/17.
 */
@Entity
@Table(name="charges")
public class Charges implements Serializable {

    private static final long serialVersionUID = -61031668766332723L;

    private int id;
    private String code;
    private int fixedCharges;
    private int consultationCharges;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true, nullable = false)
    public int getId() {
        return id;
    }

    @Column(name="code", unique = true)
    public String getCode() {
        return code;
    }

    @Column(name="fixed_charges")
    public int getFixedCharges() {
        return fixedCharges;
    }

    @Column(name="consultation_charges")
    public int getConsultationCharges() {
        return consultationCharges;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFixedCharges(int fixedCharges) {
        this.fixedCharges = fixedCharges;
    }

    public void setConsultationCharges(int consultationCharges) {
        this.consultationCharges = consultationCharges;
    }
}
