package com.springapp.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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

import com.google.gson.Gson;
import com.springapp.mvc.dto.DailyReport;
import com.springapp.mvc.dto.LoginForm;
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
    public String validateLogin(@ModelAttribute("loginDetails") Login login, Model model, HttpSession session) {
        LOG.info("username : {} , password : {}", login.getUsername(), login.getPassword());
        Login validUser = clinicService.validateLogin(login);
        System.out.println("validation done now checking");
        if (validUser == null) {
            model.addAttribute("error", "Incorrect Username/Password!!");
            return "index";
        } else if ("REC".equals(validUser.getRoleType())) {
            session.setAttribute("ROLETYPE", "REC");
            return "receptionist";
        } else if ("DOC".equals(validUser.getRoleType())) {
            session.setAttribute("ROLETYPE", "DOC");
            return "doctor";
        } else if ("MED".equals(validUser.getRoleType())) {
            session.setAttribute("ROLETYPE", "MED");
            return "medicine";
        } else if ("ADM".equals(validUser.getRoleType())) {
            session.setAttribute("ROLETYPE", "ADM");
            return "admin";
        } else {
            model.addAttribute("error", "Not a valid user. No Role Defined.");
            session.setAttribute("ROLETYPE", null);
            return "index";
        }
    }

    //to add patient details in the db
    @RequestMapping(value = "addPatientDetails", method = RequestMethod.POST)
    public String addPatientDetails(@ModelAttribute("userDetails") Patient patient, Model model, HttpSession session) {
        System.out.println("Name: " + patient.getFirstname() + " Age:" + patient.getAge() + " Sex:" + patient.getSex());

        String role = (String) session.getAttribute("ROLETYPE");

        if (clinicService.savePatientAndAddToQueue(patient) != -1) {
            model.addAttribute("addRecord", "Patient Record Successfully Added.");
            System.out.println("data inserted");
        } else {
            model.addAttribute("addRecord", "Some error occured while adding data. Please try again later.");
            System.out.println("some error occured");
        }

        //display hidden div whether data inserted successfully or not
        if (StringUtils.equals("REC", role)) {
            return "receptionist";
        } else if (StringUtils.equals("DOC", role))
            return "doctor";
        else {
            //some error occured not a valid user
            return "index";
        }
    }

    //to search for patient in the db
    @RequestMapping(value = "findPatient", method = RequestMethod.POST)
    public String findPatient(@ModelAttribute("searchForm") SearchForm search, Model model, HttpSession session) {
        LOG.info("User role : {}", session.getAttribute("ROLETYPE"));
        String role = (String) session.getAttribute("ROLETYPE");
        //if nothing is entered and search button is pressed, reload page again.
        if (ClinicUtils.isEmpty(search)) {
            System.out.println("Nothing entered");
            LOG.warn("Nothing entered for Searching Patient");
            if (StringUtils.equals("REC", role)) {
                return "receptionist";
            } else if (StringUtils.equals("DOC", role)) {
                return "doctor";
            } else {
                return "index";
            }
        }
        //fetch details of all the patients as a list.
        List<Patient> patientList = clinicService.findPatient(search);
        LOG.info("Search Query params {}", search);
        if (patientList != null) {
            LOG.info("Message : {}", patientList);
            model.addAttribute("patientList", patientList);
            return "searchResults";
        } else {
            model.addAttribute("patientList", "No Results Found");
            System.out.println("No results");
            LOG.info("No results found for the entered details.");
            if (StringUtils.equals("REC", role)) {
                return "receptionist";
            } else if (StringUtils.equals("DOC", role)) {
                return "doctor";
            } else {
                return "index";
            }
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

    //Logout from application
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        System.out.println("logging out...destroying session for : " + session.getAttribute("ROLETYPE"));
        session.setAttribute("ROLETYPE", null);
        session.invalidate();
        return "index";
    }

    //to get list of patients in the clinic
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
            System.out.println("Daily Patient Queue is empty");
            Gson gson = new Gson();
            System.out.println(gson.toJson(patientQueue));
            return gson.toJson(patientQueue);
        }
    }

    //list of patients who will take medicine
    @RequestMapping(value = "getMedicineInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getMedicineInfo(Model model) {
        List<Medicine> medicineList = clinicService.getLatestPrescription();
        //System.out.println("list " + medicineList);
        if (medicineList != null) {
            Gson gson = new Gson();
            System.out.println("medicine : " + gson.toJson(medicineList));
            LOG.info("medicine : {}", gson.toJson(medicineList));
            return gson.toJson(medicineList);
        } else {
            System.out.println("Medicine Queue is empty");
            Gson gson = new Gson();
            System.out.println(gson.toJson(medicineList));
            return gson.toJson(medicineList);
        }
    }

    //Generate Daily Report
    @RequestMapping(value = "generateDailyReport", method = RequestMethod.GET)
    @ResponseBody
    public String generateDailyReport() {
        List<DailyReport> dailyReportList = clinicService.generateDailyReport();
        System.out.println("Daily Report: " + dailyReportList);
        if (dailyReportList != null) {
            return new Gson().toJson(dailyReportList);
        } else {
            System.out.println("Daily Report is empty");
            return new Gson().toJson(dailyReportList);
        }
    }

    //Adding new user to clinic app
    @RequestMapping(value = "addUserLogin", method = RequestMethod.POST)
    public String addUserLogin(@ModelAttribute("userlogin") Login login, Model model) {
        System.out.println("Info received at controller :" + login);
        if (clinicService.addUserLogin(login)) {
            model.addAttribute("addLoginDetails", "New User Successfully Added.");
            System.out.println("login details inserted");
        } else {
            model.addAttribute("addLoginDetails", "Some error occured while adding data. Please try again later.");
            System.out.println("some error occured false returned on controller");
        }
        return "admin";
    }

    //Get all users of clinic app
    @RequestMapping(value = "getAllUsers", method = RequestMethod.GET)
    @ResponseBody
    public String getAllUsers(Model model) {
        List<Login> loginList = clinicService.getAllUsers();
        if (loginList != null) {
            Gson gson = new Gson();
            System.out.println("loginlist : " + gson.toJson(loginList));
            return gson.toJson(loginList);
        } else {
            System.out.println("Login details not found");
            Gson gson = new Gson();
            System.out.println("login list empty: " + gson.toJson(loginList));
            return gson.toJson(loginList);
        }
    }

    //Delete login details of a user
    @RequestMapping(value = "deleteUser", method = RequestMethod.GET)
    @ResponseBody
    public String deleteUser(@RequestParam(value = "id") int id, Model model) {
        if (clinicService.deleteUser(id)) {
            return "true";
        }
        return "false";
    }

    //get login details of user by id
    @RequestMapping(value = "getUserById", method = RequestMethod.GET)
    @ResponseBody
    public String getUserById(@RequestParam(value = "id") int id, Model model) {
        System.out.println("id : " + id);
        Login login = clinicService.getUserById(id);
        if (login != null) {
            Gson gson = new Gson();
            System.out.println("loginlist : " + gson.toJson(login));
            return gson.toJson(login);
        } else {
            System.out.println("Login details not found");
            Gson gson = new Gson();
            System.out.println("login details for id: " + id + gson.toJson(login));
            return gson.toJson(login);
        }
    }

    //save the updated user details
    @RequestMapping(value = "updateUserDetails", method = RequestMethod.POST)
    public String updateUserDetails(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
        System.out.println("Entered details : " + loginForm);
        if (clinicService.updateUserDetails(loginForm)) {
            System.out.println("Details updated successfully");
            model.addAttribute("updateMsg", "User Details Updated!");
        } else {
            System.out.println("update failed");
            model.addAttribute("updateMsg", "Unable to update. Please try again later.");
        }
        return "admin";
    }

    //find patient details by id
    @RequestMapping(value = "findPatientById", method = RequestMethod.GET)
    @ResponseBody
    public String findPatientById(@RequestParam(value = "id") int id, Model model) {
        System.out.println("find patient for id : " + id);
        Patient patient = clinicService.findPatientById(id);
        if (patient != null) {
            Gson gson = new Gson();
            System.out.println("Patient details : " + gson.toJson(patient));
            return gson.toJson(patient);
        } else {
            Gson gson = new Gson();
            System.out.println("Patient details for id : " + gson.toJson(patient));
            return gson.toJson(patient);
        }
    }

    //add patient details to queue
    @RequestMapping(value = "addPatientToQueue", method = RequestMethod.GET)
    @ResponseBody
    public String addPatientToQueue(@RequestParam(value = "id") int id, Model model) {
        System.out.println("id : " + id);
        if (clinicService.addPatientToQueue(id)) {
            return "Patient successfully added to queue.";
        } else {
            return "Unable to add patient to queue.";
        }
    }

    //save edited patient details
    @RequestMapping(value = "savePatientDetails", method = RequestMethod.POST)
    public String savePatientDetails(@ModelAttribute("userDetails") Patient patient, Model model, HttpSession session) {
        System.out.println("Details to be updated : " + patient);
        //String role = (String) session.getAttribute("ROLETYPE");
        if (clinicService.savePatientDetails(patient)) {
            model.addAttribute("updatePatientStatus", "Patient details updated succesfully!");

        } else {
            model.addAttribute("updatePatientStatus", "Unable to update details. Try again later.");
        }

        return "searchResults";
    }

    //get details for prescription page
    @RequestMapping(value = "givePrescription", method = RequestMethod.POST)
    @ResponseBody
    public String givePrescription(@RequestParam(value = "id") int id, Model model) {
        System.out.println("Prescription for id : " + id);
        Patient patient = clinicService.findPatientById(id);
        model.addAttribute("message","passing data successfully");
        Gson gson = new Gson();
        return gson.toJson(patient);
    }
    
    //test prescrription page
    @RequestMapping(value = "pres", method = RequestMethod.GET)
    public String pres() {
        return "prescription";
    }
}
