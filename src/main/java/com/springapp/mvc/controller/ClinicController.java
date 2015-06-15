package com.springapp.mvc.controller;

import com.springapp.mvc.dto.SearchForm;
import com.springapp.mvc.entity.Login;
import com.springapp.mvc.entity.User;
import com.springapp.mvc.service.IClinicService;
import com.springapp.mvc.utils.ClinicUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by aashish on 13/6/15.
 */
@Controller
@RequestMapping("/")
public class ClinicController {
    private static final Logger LOG = LoggerFactory.getLogger(ClinicController.class);

    @Autowired
    private IClinicService clinicService;

/*    @RequestMapping(method = {RequestMethod.GET,RequestMethod.HEAD})
    public String printWelcome(ModelMap model) {
        model.addAttribute("message", "DahiyaClinic");

        User user = clinicService.getdetails();
        System.out.println("Clinic Controller User : " + user.getFirstname());
        LOG.info("Clinic Controller User {} " + user.getFirstname());
        model.addAttribute("user",user.getFirstname()+" "+ user.getLastname());
        return "hello";
    }*/

    //To take to the login screen
    @RequestMapping(value = "/", method = {RequestMethod.GET,RequestMethod.HEAD})
    public String userLogin() {
        return "index";
    }

    //to validate login
    @RequestMapping(value = "validateLogin", method = RequestMethod.GET)
    public String validateLogin(@ModelAttribute("loginDetails") Login login,Model model) {
        LOG.info("username : {} , password : {}", login.getUsername(),
                login.getPassword());
        Login validUser = clinicService.validateLogin(login);
        if (validUser == null) {
            model.addAttribute("error", "Incorrect Username/Password!!");
            return "index";
        } else if("REC".equals(validUser.getRoleType())){
            return "receptionist";
        }
        else {
            //dummy jsp success to test the things
            model.addAttribute("username", login.getUsername());
            model.addAttribute("password", login.getPassword());
            return "success";
        }
    }

    //to add patient details in the db
    @RequestMapping(value="addPatientDetails",method=RequestMethod.POST)
    public String addPatientDetails(@ModelAttribute("userDetails")User user,Model model){
        System.out.println("Name: " + user.getFirstname()+" Age:"+user.getAge()+ " Sex:" + user.getSex());
        if(clinicService.persistPatientDetails(user)){
            System.out.println("data inserted");
        }
        else{
            System.out.println("some error occured");
        }
        //display hidden div whether data inserted successfully or not
        return "success";
    }

    //to search for patient in the db
    @RequestMapping(value="findPatient",method=RequestMethod.POST)
    public String findPatient(@ModelAttribute("searchForm") SearchForm search,Model model){

        //if nothing is entered and button is pressed, reload page again.
        if (ClinicUtils.isEmpty(search)) {
            return "receptionist";
        }
        //fetch details of all the patients as a list.
        List<User> patientList = clinicService.findPatient(search);

        model.addAttribute("patientList",patientList);
        return "searchResults";
    }
}
