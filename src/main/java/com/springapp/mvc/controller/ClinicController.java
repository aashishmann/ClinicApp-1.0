package com.springapp.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.service.IClinicService;
import com.springapp.mvc.utils.ClinicUtils;

/**
 * Created by aashish on 13/6/15.
 */
@Controller
@RequestMapping("/")
@SessionAttributes("validUser")
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
        } else {
            //dummy jsp success to test the things
            model.addAttribute("username", login.getUsername());
            model.addAttribute("password", login.getPassword());
            return "success";
        }
    }

    //to add patient details in the db
    @RequestMapping(value = "addPatientDetails", method = RequestMethod.POST)
    public String addPatientDetails(@ModelAttribute("userDetails") User user, Model model) {
        System.out.println("Name: " + user.getFirstname() + " Age:" + user.getAge() + " Sex:" + user.getSex());
        if (clinicService.persistPatientDetails(user)) {
            model.addAttribute("addRecord","Patient Record Successfully Added.");
            System.out.println("data inserted");
        } else {
            model.addAttribute("addRecord","Some error occured while adding data. Please try again later.");
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
            return "receptionist";
        }
        //fetch details of all the patients as a list.
        List<User> patientList = clinicService.findPatient(search);
        System.out.println("Printing data to be searched");
        System.out.println(search);
        model.addAttribute("patientList", patientList);
        return "searchResults";
    }

    @RequestMapping(value = "deletePatient/{id}", method = RequestMethod.GET)
    public String deletePatient(@PathVariable("id") int id, Model model) {
        //System.out.println(clinicService.findPatientById(id));
        //User user=clinicService.findPatientById(id);
        clinicService.deletePatient(id);
        return "receptionist";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        System.out.println("logging out");
        session.invalidate();
        return "index";
    }
    
    @RequestMapping(value = "getQueueInfo", method = RequestMethod.GET)
    public String getQueueInfo(Model model){
        List<User> patientQueue = clinicService.getQueueInfo();
        return null;
    }
}
