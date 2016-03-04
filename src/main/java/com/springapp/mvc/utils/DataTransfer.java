package com.springapp.mvc.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.healthmarketscience.jackcess.*;

public class DataTransfer {

    public static void main(String[] args) {

        Database db = null;
        TableBuilder mytabbuild = null;
        Table myTable = null;

        try {
            File dbFile = new File("/home/arti/Downloads/Clinic_data/DB1.MDB");

            db = DatabaseBuilder.open(dbFile);
            System.out.println("tables: " + db.getTableNames());
            
            myTable = db.getTable("Medicines");
            for (Column col : myTable.getColumns()) {
                System.out.println("column: " + col.getName());
            }
            for (Row row : myTable) {
                System.out.println("Row :" + row);
            }

        } catch (IOException e) {
            System.out.println("ERROR: problem building access db and table!");
            e.printStackTrace();
        }
        System.out.println("successfully created access db");

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

}
