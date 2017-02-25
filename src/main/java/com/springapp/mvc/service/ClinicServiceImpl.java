package com.springapp.mvc.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.springapp.mvc.entity.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.springapp.mvc.dao.IClinicDao;
import com.springapp.mvc.dto.DailyReport;
import com.springapp.mvc.dto.LoginForm;
import com.springapp.mvc.dto.Medicine;
import com.springapp.mvc.dto.MonthlyReport;
import com.springapp.mvc.dto.PrescriptionDTO;
import com.springapp.mvc.dto.SearchForm;

/**
 * Created by aashish on 3/6/15.
 */
@Service
@EnableScheduling
public class ClinicServiceImpl implements IClinicService {

    private static final Logger LOG = LoggerFactory.getLogger(ClinicServiceImpl.class);

    @Autowired
    IClinicDao                  clinicDao;

    @Transactional
    @Override
    public Patient getdetails() {
        return clinicDao.getdetails();
    }

    @Transactional(readOnly = true)
    @Override
    public Login validateLogin(Login login) {
        return clinicDao.validateLogin(login.getUsername(), login.getPassword());
    }

    @Transactional
    @Override
    public int persistPatientDetails(Patient patient) {
        return clinicDao.persistPatientDetails(patient);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Patient> findPatient(SearchForm search) {
        return clinicDao.findPatient(search);
    }

    @Transactional
    @Override
    public boolean deletePatient(int id) {
        return clinicDao.deletePatient(id);
    }

    @Transactional
    @Override
    public void updatePatientDetails(Patient patient) {
        clinicDao.updatePatientDetails(patient);
    }

    @Transactional(readOnly = true)
    @Override
    public Patient findPatientById(int id) {
        return clinicDao.findPatientById(id);
    }

    @Transactional
    @Override
    public Boolean persistPatientHistory(PatientHistory patientHistory) {
        return clinicDao.persistPatientHistory(patientHistory);
    }

    @Transactional
    @Override
    public List<PatientQueue> getQueueInfo() {
        List<PatientQueue> queue = clinicDao.getQueueInfo();
        if (!CollectionUtils.isEmpty(queue)) {
            Collections.reverse(queue);
        }
        return queue;
    }

    @Override
    public int savePatientAndAddToQueue(Patient patient) {
        int id = persistPatientDetails(patient);
        if (id < 0) {
            System.out.println("Unable to persist patient details in patient table");
            return id;
        }

        if (addPatientToQueue(id)) {
            System.out.println("Patient details added to queue");
        } else {
            System.out.println("Some error occured while adding patient details to queue");
        }
        return id;
    }

    @Transactional
    @Override
    public void updatePatientHistory(PatientHistory patientHistory) {
        clinicDao.updatePatientHistory(patientHistory);
    }

    @Transactional
    @Override
    public void updatePrescription(Prescription prescription) {
        clinicDao.updatePrescription(prescription);
    }

    /*
     * Displays information on medicine page. i.e., medicine queue
     * Fetch patient ids from patient queue and get latest prescription for them.
     */
    @Transactional(readOnly = true)
    @Override
    public List<Medicine> getLatestPrescription() {

        List<PatientQueue> patientQueues = clinicDao.getQueueInfo();
        //List returned is null in case queue is empty
        if (patientQueues == null) {
            return null;
        }

        List<Integer> patientIds = new ArrayList<Integer>();
        for (PatientQueue patientQueue : patientQueues) {
            int patientId = patientQueue.getPatient().getId();
            patientIds.add(patientId);
        }

        List<Prescription> prescriptions = clinicDao.getLatestPrescription(patientIds);
        System.out.println("Prescriptions size : " + prescriptions.size());
        if (prescriptions.size() > 0) {
            List<Medicine> medicines = new ArrayList<Medicine>();
            for (Prescription prescription : prescriptions) {
                Medicine medicine = new Medicine();

                medicine.setPatientId(prescription.getPatient().getId());
                medicine.setFirstname(prescription.getPatient().getFirstname());
                medicine.setLastname(prescription.getPatient().getLastname());
                medicine.setMedicines(prescription.getMedicines());
                medicine.setCharges(prescription.getCharges().getFixedCharges() + prescription.getCharges().getConsultationCharges());

                medicines.add(medicine);
            }
            return medicines;
        } else {
            //Prescriptions returned are null
            return null;
        }
    }

    @Transactional
    @Override
    public boolean deleteFromQueue(int id) {
        return clinicDao.deleteFromQueue(id);
    }

    @Transactional
    @Override
    public boolean addPatientToQueue(int patientId) {
        PatientQueue patientQueue = new PatientQueue();
        patientQueue.setPatient(findPatientById(patientId));
        patientQueue.setEntryTime(new Date());

        return clinicDao.addToQueue(patientQueue);
    }

    @Transactional(readOnly = true)
    @Override
    public PatientHistory getPatientHistory(int patientId) {
        return clinicDao.getPatientHistory(patientId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PrescriptionDTO> getFiveLatestPrescriptions(int patientId) {
        List<Prescription> presList = clinicDao.getFiveLatestPrescriptions(patientId);
        List<PrescriptionDTO> presDTOList = new ArrayList<>();
        for(Prescription pres : presList) {
            presDTOList.add(getPrescriptionDTO(pres));
        }
        return presDTOList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DailyReport> generateDailyReport() {
        List<Prescription> presciptionList = clinicDao.generateDailyReport();
        return prescriptionToDailyReportConvertor(presciptionList);
    }

    private List<DailyReport> prescriptionToDailyReportConvertor(List<Prescription> prescriptionList) {
        List<DailyReport> dailyReportList = new ArrayList<DailyReport>();
        if (prescriptionList != null) {
            for (Prescription prescription : prescriptionList) {
                DailyReport dailyReport = new DailyReport();
                dailyReport.setFirstname(prescription.getPatient().getFirstname());
                dailyReport.setLastname(prescription.getPatient().getLastname());
                dailyReport.setCharges(prescription.getCharges().getFixedCharges() + prescription.getCharges().getConsultationCharges());
                dailyReportList.add(dailyReport);
            }
        }
        return dailyReportList;
    }

    @Transactional
    @Override
    public boolean addUserLogin(Login login) {
        return clinicDao.addUserLogin(login);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Login> getAllUsers() {
        return clinicDao.getAllUsers();
    }

    @Transactional
    @Override
    public boolean deleteUser(int id) {
        return clinicDao.deleteUser(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Login getUserById(int id) {
        return clinicDao.getUserById(id);
    }

    @Transactional
    @Override
    public boolean updateUserDetails(LoginForm loginForm) {
        Login login = new Login();
        login.setId(loginForm.getId());
        login.setUsername(loginForm.getUsername());
        login.setPassword(loginForm.getPassword());
        login.setRoleType(loginForm.getRoleType());

        return clinicDao.updateUserDetails(login);
    }

    @Transactional
    @Override
    public boolean savePatientDetails(Patient patient) {
        Patient existingPatient = findPatientById(patient.getId());
        if (existingPatient != null) {
            existingPatient.setFirstname(patient.getFirstname());
            existingPatient.setLastname(patient.getLastname());
            existingPatient.setAge(patient.getAge()); //birthyear to be set later on
            existingPatient.setAddress(patient.getAddress());
            existingPatient.setDependent(patient.getDependent());
            existingPatient.setLandline(patient.getLandline());
            existingPatient.setMobile(patient.getMobile());
            existingPatient.setMaritalStatus(patient.getMaritalStatus());
            existingPatient.setOccupation(patient.getOccupation());
            existingPatient.setRefferedBy(patient.getRefferedBy());
            existingPatient.setSex(patient.getSex());

            return clinicDao.savePatientDetails(existingPatient);
        }
        return false;
    }

    private PrescriptionDTO getPrescriptionDTO(Prescription pres) {
        PrescriptionDTO presDTO = new PrescriptionDTO();
        presDTO.setPatientId((int) pres.getId());
        if(pres.getCharges() != null)
            presDTO.setCharges(new Integer(pres.getCharges().getFixedCharges() + pres.getCharges().getConsultationCharges()).toString());
        presDTO.setFollowupRemark(pres.getFollowupRemark());
        if(pres.getEntryTime() != null)
            presDTO.setEntryTime(new java.sql.Date(pres.getEntryTime().getTime()));
        presDTO.setMedicines(pres.getMedicines());
        if(pres.getRevisitDate() != null)
            presDTO.setRevisitDate(new java.sql.Date(pres.getRevisitDate().getTime()));
        return presDTO;
    }

    @Transactional
    @Override
    public boolean addPrescription(PrescriptionDTO prescriptionDTO) {
        //converting prescription dto to prescription
        Prescription prescription = new Prescription();
        LOG.info("Converting prescription dto to prescription");
        prescription.setMedicines(prescriptionDTO.getMedicines());
        prescription.setFollowupRemark(prescriptionDTO.getFollowupRemark());
        prescription.setCharges(findChargesByCode(prescriptionDTO.getCharges()));
        prescription.setPatient(findPatientById(prescriptionDTO.getPatientId()));
        prescription.setRevisitDate(prescriptionDTO.getRevisitDate());
        //set current time as entry time
        prescription.setEntryTime(new Date());

        return clinicDao.addPrescription(prescription);
    }

    @Override
    @Scheduled(fixedDelay = 86400000)
    public void deleteFromQueue() {
        LOG.info("Cron job to delete previous date entries from the patient Queue {}");
        clinicDao.deleteFromQueue();
    }

    @Transactional(readOnly = true)
    @Override
    public List<MonthlyReport> generateMonthlyReport(int month, int year) {
        // Get the dates for which report is to be generated
        List<Date> dates = getDates(month, year);
        if (dates.isEmpty()) {
            LOG.info("Date conversion failed.");
            // TODO:return from here  -- IMPORTANT
        }

        LOG.info("Calling dao to get prescription info. for month : {} and year : {}", month, year);
        // Get the data in the range for report generation.
        List<Prescription> prescriptions = clinicDao.generateMonthlyReport(dates);
        LOG.info("Prescriptions in range: {} are : {}", dates, prescriptions);

        return getMonthlyReport(prescriptions);
    }

    // Calculate start and end dates and return them.
    private List<Date> getDates(int month, int year) {

        // Format in which date is needed.
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        int endDay = 30;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            endDay = 31;
        } else if (month == 2 && ((year % 4 == 0 && year != 0) || (year % 400 == 0))) {
            endDay = 29;
        } else if (month == 2) {
            endDay = 28;
        }

        String startDateString = year + "-0" + month + "-01";
        String endDateString = year + "-0" + month + "-" + endDay;

        LOG.info("startDateString : {} and endDateString : {}", startDateString, endDateString);

        try {
            Date startdate = formatter.parse(startDateString);
            Date endDate = formatter.parse(endDateString);
            LOG.info("Dates after parsing, startDate : {} and endDate : {}", startdate, endDate);
            List<Date> dates = new ArrayList<Date>();
            dates.add(startdate);
            dates.add(endDate);

            return dates;
        } catch (ParseException e) {
            LOG.error("Some error occured while formatting date : ", e);
        }

        // Don't return null if exception occurs or dates are not converted properly.
        return new ArrayList<Date>();
    }

    // This method converts the prescriptions to the Monthly Report format.
    private List<MonthlyReport> getMonthlyReport(List<Prescription> prescriptions) {
        if (prescriptions != null && !prescriptions.isEmpty()) {
            List<MonthlyReport> monthlyReports = new ArrayList<MonthlyReport>();
            for (Prescription pres : prescriptions) {
                MonthlyReport mReport = new MonthlyReport(pres.getPatient().getFirstname(), pres.getPatient().getLastname(), pres.getCharges().getFixedCharges() + pres.getCharges().getConsultationCharges(), pres.getEntryTime());
                monthlyReports.add(mReport);
            }
            return monthlyReports;
        }
        return null;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getAllAmountLabels() {
        return clinicDao.getAllAmountLabels();
    }

    @Transactional(readOnly = true)
    @Override
    public Charges findChargesByCode(String code) {
        return clinicDao.findChargesByCode(code);
    }
}
