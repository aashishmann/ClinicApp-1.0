package com.springapp.mvc.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

public class DataTransfer {

    public static void main(String[] args) {

        Database db = null;
        Table myTable = null;

        //MySQL  Database credentials
        final String DB_URL = "jdbc:mysql://localhost/clinic";
        final String USER = "root";
        final String PASS = "snap@123";

        Connection conn = null;
        Statement stmt = null;

        Map<String, String> tables = new HashMap<String, String>();
        tables.put("Amount Code", "amount");
        tables.put("Medicine Details", "med_details");
        tables.put("Medicines", "Medicines");
        tables.put("Patient", "Patient");
        tables.put("x", "x");
        tables.put("xx", "med_details");

        try {
            File dbFile = new File("/home/arti/Downloads/Clinic_data/DB1.MDB");

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

            sql = "CREATE TABLE IF NOT EXISTS  med_details (id INTEGER not NULL AUTO_INCREMENT, patient_card_no VARCHAR(400), date datetime, medicine VARCHAR(400), amt_code VARCHAR(100), note VARCHAR(255), medicine1 VARCHAR(400), PRIMARY KEY (id))";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS Patient (id INTEGER not NULL AUTO_INCREMENT, p_id INTEGER not NULL, patient_card_no VARCHAR(400), name VARCHAR(400), address VARCHAR(510), age INTEGER DEFAULT 0, sex VARCHAR(2), problem VARCHAR(500), diagnosis VARCHAR(1500), PRIMARY KEY (id))";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            for (String tableName : db.getTableNames()) {
                System.out.println(tableName);
                myTable = db.getTable(tableName);

                if (!tableName.equals("x")) {
                    stmt = conn.createStatement();
                    for (Row row : myTable) {
                        sql = getSQLInsertQuery(tables.get(tableName), myTable, row);
                        stmt.executeUpdate(sql);
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

            DateFormat readFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            DateFormat writeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
        System.out.println(sql);
        return sql;
    }

}
