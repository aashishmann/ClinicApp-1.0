package com.springapp.mvc.utils;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import com.springapp.mvc.entity.OldDB.OldMedicalDetails;
import com.springapp.mvc.entity.OldDB.OldPatient;
import org.apache.commons.lang.StringUtils;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class AccessToSqlMigrationUtil {

    public static DateFormat readFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy",Locale.ENGLISH);
    public static DateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);

    public static void main(String[] args) {

        Database db = null;
        Table myTable = null;


        //MySQL  Database credentials
        //final String DB_URL = "jdbc:mysql://localhost/clinic?rewriteBatchedStatement=true&autoReconnect=true";
        final String DB_URL = "jdbc:mysql://devdb.ggn.snapdeal.com:3306/snap_temp?rewriteBatchedStatements=true&autoReconnect=true";
        final String USER = "root";
        //final String PASS = "root";
        final String PASS = "snapdeal";
        final String DRIVER_NAME = "com.mysql.jdbc.Driver";


        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER_NAME);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);

        jdbcTemplate.setDataSource(dataSource);


        Connection conn = null;
        Statement stmt = null;

        Map<String, String> tables = new HashMap<String, String>();
        tables.put("Amount Code", "amount");
        tables.put("Medicine Details", "old_med_details");
        tables.put("Medicines", "Medicines");
        tables.put("Patient", "Patient_Old");
        tables.put("x", "x");
        // replica of medicine details
        tables.put("xx", "med_details");

        try {
            File dbFile = new File("/home/aashish/Documents/Personal/Mine/Dev/DahiyaClinic/DR.DAHIYA/DB1.MDB");

            db = DatabaseBuilder.open(dbFile);
            System.out.println("tables: " + db.getTableNames());

            //Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            System.out.println("Creating statement...");

            String sql = "CREATE TABLE IF NOT EXISTS Medicines (id INTEGER not NULL AUTO_INCREMENT, m_id INTEGER not NULL, medicine_no VARCHAR(100), medicine_name VARCHAR(100), details  VARCHAR(510), code VARCHAR(400), PRIMARY KEY (id))";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS  amount (id INTEGER not NULL AUTO_INCREMENT, amt_id INTEGER not NULL, amt_code VARCHAR(100), amount BIGINT(20) DEFAULT 0, consult_fee  BIGINT(20) DEFAULT 0, PRIMARY KEY (id))";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS  old_med_details (id INTEGER not NULL AUTO_INCREMENT, patient_card_no INT(10), medicine_date DATETIME, medicine VARCHAR(400), amt_code VARCHAR(100), note VARCHAR(255), medicine1 VARCHAR(400), PRIMARY KEY (id))";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS  med_details (id INTEGER not NULL AUTO_INCREMENT, patient_card_no INT(10), medicine_date DATETIME, medicine VARCHAR(400), amt_code VARCHAR(100), note VARCHAR(255), medicine1 VARCHAR(400), PRIMARY KEY (id))";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS old_patient (id INTEGER not NULL AUTO_INCREMENT, patient_card_no INT(10), name VARCHAR(400), address VARCHAR(510), age INTEGER DEFAULT 0, sex VARCHAR(2), problem VARCHAR(500), diagnosis VARCHAR(1500), PRIMARY KEY (id)), KEY `pcn` (`patient_card_no`)";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            for (String tableName : db.getTableNames()) {
                System.out.println(tableName);
                myTable = db.getTable(tableName);
                if(tableName.equalsIgnoreCase("Medicine Details")) {
                        long startTime = System.currentTimeMillis();
                        saveMedicalDetails(jdbcTemplate, myTable);
                        System.out.println("Total time in saving Medical Details " + (System.currentTimeMillis() - startTime)/1000 + " secs");
                }
                else if(tableName.equalsIgnoreCase("Patient")) {
                    long startTime = System.currentTimeMillis();
                    savePatient(jdbcTemplate, myTable);
                    System.out.println("Total time in saving Patient " + (System.currentTimeMillis() - startTime)/1000 + " secs");
                }
                else if (!tableName.equals("x")) {
                    System.out.println("Storing table : " + tableName + " in linear fashion i.e non batch mode ");
                    int rowCount=0;
                    stmt = conn.createStatement();
                    long start= System.currentTimeMillis();
                    for (Row row : myTable) {
                        rowCount++;

                        sql = getSQLInsertQuery(tables.get(tableName), myTable, row);
                        try {
                            stmt.executeUpdate(sql);
                        } catch (SQLException e) {
                            System.out.println("Invalid request:" + sql);
                            e.printStackTrace();
                        }
                        if(rowCount == 1000) {
                            System.out.println("Time taken in 1000 batch size : " + (System.currentTimeMillis()-start)/1000 + " secs");
                            start=System.currentTimeMillis();
                            rowCount = 0;
                        }
                        boolean flag = false;
                        long startTime = 0;
                        if(tableName.equals("Medicine Details") && flag == false ) {
                            startTime = System.currentTimeMillis();
                            flag = true;
                        }
                        if(tableName.equals("Medicines") && flag == true ) {
                            flag = false;
                            System.out.println("Total time in saving Medical details " + (System.currentTimeMillis()-startTime)/1000 + " secs");
                        }
                    }
                }

            }

        } catch (IOException e) {
            System.out.println("ERROR: problem building access db and table!");
            e.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }

        /*try {
            System.load("/usr/lib/jvm/java-7-oracle/jre/lib/amd64/libJdbcOdbc.so");
        } catch (UnsatisfiedLinkError e) {
          e.printStackTrace();
        }
        
        try {

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

            String accessFileName = "/home/arti/Downloads/Clinic_data/DB1";
            String connURL = "jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb)};DBQ=" + accessFileName + ".mdb;";
            System.out.println("URL :" + connURL);
            
            Connection con = DriverManager.getConnection(connURL, "", "");

            Statement stmt = con.createStatement();

            stmt.execute("select * from Medicines"); // execute query in table Medicines

            ResultSet rs = stmt.getResultSet(); // get any Result that came from our query

            if (rs != null)
                while (rs.next()) {
                    System.out.println("Row : " + rs.getRow());
                    //System.out.println("Name: " + rs.getString("Name") + " ID: " + rs.getString("ID"));
                }

            stmt.close();
            con.close();
        } catch (Exception err) {
            System.out.println("ERROR: " + err.getStackTrace());
            err.printStackTrace();
        }*/
    }

    private static String getSQLInsertQuery(String tableName, Table accessTable, Row row) throws ParseException {
        String value = null;
        for (Column col : accessTable.getColumns()) {

            /*DateFormat readFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            DateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");*/

            if (StringUtils.isEmpty(value)) {
                if (col.getType().toString().equals("SHORT_DATE_TIME")) {
                    Date date = (Date) readFormat.parse(row.get(col.getName()).toString());
                    value = row.get(col.getName()) != null ? ("'" + writeFormat.format(date) + "'") : "null";
                } else {
                    value = row.get(col.getName()) != null ? ("'" + row.get(col.getName()).toString() + "'") : "null";
                }
            } else {
                if (col.getType().toString().equals("SHORT_DATE_TIME")) {
                    Date date = (Date) readFormat.parse(row.get(col.getName()).toString());
                    value = value + "," + (row.get(col.getName()) != null ? ("'" + writeFormat.format(date) + "'") : "null");
                } else {
                    value = value + "," + (row.get(col.getName()) != null ? ("'" + row.get(col.getName()).toString() + "'") : "null");
                }
            }
        }


        String sql = "INSERT INTO " + tableName + " " + "VALUES(null," + value + ")";
        //System.out.println(sql);
        return sql;
    }

    private static void saveMedicalDetails(JdbcTemplate jdbcTemplate, Table table) throws ParseException {
        List<OldMedicalDetails> mdList = new ArrayList<>();
        int i=1;
        int rows = 0;
        System.out.println("Old Medical Table Row count " + table.getRowCount());
        for (Row row : table) {
            OldMedicalDetails medDetail = new OldMedicalDetails();
            for (Column col : table.getColumns()) {
                if (col.getName().equalsIgnoreCase("Patient Card no")) {
                    if (row.get(col.getName()) != null) {
                        try {
                            medDetail.setPatientCardNumber(Integer.parseInt(row.get(col.getName()).toString()));
                        }catch(NumberFormatException n){
                            int patientCardNumber = convertStringToInt(row.get(col.getName()).toString());
                            System.out.println("patient card number caught in exception" + row.get(col.getName()).toString() + " and changed to " + patientCardNumber);
                            //medDetail.setPatientCardNumber(patientCardNumber);
                        }
                    }
                } else if (col.getName().equalsIgnoreCase("date")) {
                    if (row.get(col.getName()) != null) {
                        Date date = (Date) readFormat.parse(row.get(col.getName()).toString());
                        medDetail.setMedicineDate((Date)(writeFormat.parse(writeFormat.format(date))));
                    }
                    else {
                        System.out.println("Null date received");
                    }
                } else if (col.getName().equalsIgnoreCase("medicine")) {
                    if(row.get(col.getName()) != null)
                        medDetail.setMedicine(row.get(col.getName()).toString());
                } else if (col.getName().equalsIgnoreCase("amt_code")) {
                    if(row.get(col.getName()) != null)
                        medDetail.setAmountCode(row.get(col.getName()).toString());
                } else if (col.getName().equalsIgnoreCase("note")) {
                    if(row.get(col.getName()) != null)
                        medDetail.setNote(row.get(col.getName()).toString());
                } else if (col.getName().equalsIgnoreCase("Medicine_1")) {
                    if(row.get(col.getName()) != null)
                        medDetail.setNote(row.get(col.getName()).toString());
                } else {
                    System.out.println("Invalid Coloumn Name : " + col.getName() + " in Medical Details Table");
                }
            }
            rows++;
            mdList.add(medDetail);
            if (mdList.size() == 5000) {
                long start = System.currentTimeMillis();
                saveMedicalDetailsBatch(jdbcTemplate, mdList);
                System.out.println("saving " + i + " batch for MedicalDetails in " + (System.currentTimeMillis() - start)/1000 + " secs");
                mdList.clear();
                i++;
            }
        }
        System.out.println("Total rows " + rows);
        System.out.println("saving last " + i + " batch for MedicalDetails");
        saveMedicalDetailsBatch(jdbcTemplate,mdList);

    }

    private static void saveMedicalDetailsBatch(JdbcTemplate jdbcTemplate, final List<OldMedicalDetails> mdList) {
        String sql = "insert into old_med_details(patient_card_no,medicine_date,medicine,amt_code,note,medicine1) values (?,?,?,?,?,?) ";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                OldMedicalDetails medicalDetail = mdList.get(i);

                ps.setInt(1, medicalDetail.getPatientCardNumber());

                if(medicalDetail.getMedicineDate() != null) {
                    ps.setTimestamp(2, new java.sql.Timestamp(medicalDetail.getMedicineDate().getTime()));
                }
                else {
                    ps.setNull(2, Types.TIMESTAMP);
                }

                ps.setString(3, medicalDetail.getMedicine());
                ps.setString(4, medicalDetail.getAmountCode());
                ps.setString(5, medicalDetail.getNote());
                ps.setString(6, medicalDetail.getMedicine1());
            }

            @Override
            public int getBatchSize() {
                return mdList.size();
            }
        });
    }

    private static void savePatient(JdbcTemplate jdbcTemplate, Table table) throws ParseException {
        List<OldPatient> patientList = new ArrayList<>();
        int i=1;
        int rows = 0;
        System.out.println("Patient Table Row count " + table.getRowCount());
        for (Row row : table) {
            OldPatient patient = new OldPatient();
            for (Column col : table.getColumns()) {
                if (col.getName().equalsIgnoreCase("Patient Card no")) {
                    if (row.get(col.getName()) != null) {
                        try {
                            patient.setPatientCardNumber(Integer.parseInt(row.get(col.getName()).toString()));
                        }catch(NumberFormatException n){
                            int patientCardNumber = convertStringToInt(row.get(col.getName()).toString());
                            System.out.println("patient card number caught in exception" + row.get(col.getName()).toString() + " and changed to " + patientCardNumber);
                            //patient.setPatientCardNumber(patientCardNumber);
                        }
                    }
                } else if (col.getName().equalsIgnoreCase("Patient Name")) {
                    if(row.get(col.getName()) != null)
                        patient.setName(row.get(col.getName()).toString());
                } else if (col.getName().equalsIgnoreCase("address")) {
                    if(row.get(col.getName()) != null)
                        patient.setAddress(row.get(col.getName()).toString());
                } else if (col.getName().equalsIgnoreCase("age")) {
                    if(row.get(col.getName()) != null)
                        try {
                            patient.setAge((int)Float.parseFloat((row.get(col.getName()).toString())));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Patient age " + row.get(col.getName()) + " in Patient");
                        }
                } else if (col.getName().equalsIgnoreCase("sex")) {
                    if(row.get(col.getName()) != null)
                        patient.setSex(row.get(col.getName()).toString());
                }else if (col.getName().equalsIgnoreCase("problem")) {
                    if(row.get(col.getName()) != null)
                        patient.setProblem(row.get(col.getName()).toString());
                }else if (col.getName().equalsIgnoreCase("diagnosis")) {
                    if(row.get(col.getName()) != null) {
                        patient.setDiagnosis(row.get(col.getName()).toString());
                        if(rows==3569) {
                            System.out.println("UTF-8 diagnosis " + patient.getDiagnosis());
                            System.out.println("UTF-8 diagnosis " + row.get(col.getName()).toString());
                            patient.setDiagnosis(null);
                        }
                    }
                }else if(col.getName().equalsIgnoreCase("id")) {
                }else {
                    System.out.println("Invalid Coloumn Name : " + col.getName() + " in Old Patient Table");
                }
            }
            rows++;
            patientList.add(patient);
            if (patientList.size() == 5000) {
                long start = System.currentTimeMillis();
                savePatientsBatch(jdbcTemplate, patientList);
                System.out.println("saving " + i + " batch for Patient in " + (System.currentTimeMillis() - start)/1000 + " secs");
                patientList.clear();
                i++;
            }
        }
        System.out.println("Total rows " + rows);
        System.out.println("saving last " + i + " batch for Patient");

        try {
            savePatientsBatch(jdbcTemplate, patientList);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

    }

    private static void savePatientsBatch(JdbcTemplate jdbcTemplate, final List<OldPatient> patientList) throws DataAccessException {
        String sql = "insert into old_patient(patient_card_no,name,address,age,sex,problem,diagnosis) values (?,?,?,?,?,?,?) ";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                OldPatient patientDetail = patientList.get(i);

                ps.setInt(1, patientDetail.getPatientCardNumber());
                ps.setString(2, patientDetail.getName());
                ps.setString(3, patientDetail.getAddress());
                ps.setInt(4, patientDetail.getAge());
                ps.setString(5, patientDetail.getSex());
                ps.setString(6, patientDetail.getProblem());
                ps.setString(7, patientDetail.getDiagnosis());
            }

            @Override
            public int getBatchSize() {
                return patientList.size();
            }
        });
    }

    private static int convertStringToInt(String str) {
        int i = 0;
        for (char ch : str.toCharArray()) {
            if (ch == '~' || ch == '`' || ch == '!') {
                i =i*10+ 1;
            } else if (ch == '@') {
                i =i*10+ 2;
            } else if (ch == '-' || ch == '_') {
                i =i*10+ 3;
            } else if (ch == '/') {
                i =i*10+ 4;
            } else if (ch >= 'a' && ch <= 'z') {
                i =i*10+ (ch - 'a'+1);
            } else if (ch >= 'A' && ch <= 'Z') {
                i =i*10+ (ch - 'A'+1);
            } else if (ch >= '0' && ch <= '9') {
                i =i*10+ (ch - '0');
            }
        }
        return i;
    }
}
