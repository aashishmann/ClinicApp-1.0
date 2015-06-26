package com.springapp.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;
import com.springapp.mvc.dto.Medicine;
import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.Patient;
import com.springapp.mvc.entity.PatientQueue;
import com.springapp.mvc.service.IClinicService;
import com.springapp.mvc.utils.ClinicUtils;

/**
 * Created by aashish on 13/6/15.
 */
@Controller
@RequestMapping("/")
@SessionAttributes("validUser")
//for maintaining session will be used later
public class ClinicController {
    private static final Logger LOG = LoggerFactory.getLogger(ClinicController.class);

    @Autowired
    private IClinicService      clinicService;

    //To take to the login screen
    @RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.HEAD })
    public String userLogin() {
        return "index";
    }

    @RequestMapping(value = "receptionist", method = { RequestMethod.GET, RequestMethod.HEAD })
    public String receptionist() {
        return "receptionist";
    }

    //to validate login
    @RequestMapping(value = "validateLogin", method = RequestMethod.GET)
    public String validateLogin(@ModelAttribute("loginDetails") Login login, Model model) {
        LOG.info("username : {} , password : {}", login.getUsername(), login.getPassword());
        Login validUser = clinicService.validateLogin(login);
        System.out.println("validation done now checking");
        if (validUser == null) {
            model.addAttribute("error", "Incorrect Username/Password!!");
            return "index";
        } else if ("REC".equals(validUser.getRoleType())) {
            return "receptionist";
        } else if ("DOC".equals(validUser.getRoleType())) {
            return "doctor";
        } else if ("MED".equals(validUser.getRoleType())) {
            return "medicine";
        } else {
            //dummy jsp success to test the things
            model.addAttribute("username", login.getUsername());
            model.addAttribute("password", login.getPassword());
            return "success";
        }
    }

    //to add patient details in the db
    @RequestMapping(value = "addPatientDetails", method = RequestMethod.POST)
    public String addPatientDetails(@ModelAttribute("userDetails") Patient patient, Model model) {
        System.out.println("Name: " + patient.getFirstname() + " Age:" + patient.getAge() + " Sex:" + patient.getSex());
        if (clinicService.savePatientAndAddToQueue(patient) != -1) {
            model.addAttribute("addRecord", "Patient Record Successfully Added.");
            System.out.println("data inserted");
        } else {
            model.addAttribute("addRecord", "Some error occured while adding data. Please try again later.");
            System.out.println("some error occured");
        }
        //display hidden div whether data inserted successfully or not
        return "receptionist";
    }

    //to search for patient in the db
    @RequestMapping(value = "findPatient", method = RequestMethod.POST)
    public String findPatient(@ModelAttribute("searchForm") SearchForm search, Model model) {

        //if nothing is entered and button is pressed, reload page again.
        if (ClinicUtils.isEmpty(search)) {
            System.out.println("Nothing entered");
            LOG.warn("Nothing entered for Searching Patient");
            return "receptionist";
        }
        //fetch details of all the patients as a list.
        List<Patient> patientList = clinicService.findPatient(search);
        //System.out.println("Printing data to be searched");
        //System.out.println(search);
        LOG.info("Search Query params {}", search);
        if (patientList != null) {
            LOG.info("Message : {}", patientList);
            model.addAttribute("patientList", patientList);
            return "searchResults";
        } else {
            model.addAttribute("patientList", "No Results Found");
            System.out.println("No results");
            return "receptionist";
        }
    }

    @RequestMapping(value = "deletePatient", method = RequestMethod.GET)
    @ResponseBody
    public String deletePatient(@RequestParam(value = "id") int id, Model model) {
        //System.out.println(clinicService.findPatientById(id));
        //Patient patient=clinicService.findPatientById(id);
        if (clinicService.deletePatient(id)) {
            return "Patient Record Deleted!";
        } else {
            return "Some error occured while deleting.";
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        System.out.println("logging out");
        session.invalidate();
        return "index";
    }

    @RequestMapping(value = "getQueueInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getQueueInfo(Model model) {
        System.out.println("get queue info at controller");
        List<PatientQueue> patientQueue = clinicService.getQueueInfo();
        if (patientQueue != null) {
            //System.out.println(patientQueue);
            Gson gson = new Gson();
            System.out.println(gson.toJson(patientQueue));
            return gson.toJson(patientQueue);
        } else {
            //modify later
            return null;
        }
    }

    @RequestMapping(value = "getMedicineInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getMedicineInfo(Model model) {
        List<Medicine> medicineList = clinicService.getLatestPrescription();
        //System.out.println("list " + medicineList);
        if (medicineList != null) {
            Gson gson = new Gson();
            System.out.println(gson.toJson(medicineList));
            return gson.toJson(medicineList);
        } else {
            //modify later
            return null;
        }
    }
}
