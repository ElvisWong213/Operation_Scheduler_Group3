package user;

import java.sql.*;
import java.time.*;

import dataStructure.*;
import database.Database;

public class UserManager {
    public static MyLinkedList<Professional> getAllProfessionals() {
        MyLinkedList<Professional> professionals = new MyLinkedList<>();
        Database db = null;
        try {
            db = new Database();
            String query = "SELECT professional_id FROM professional";
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                Professional professional = new Professional();
                professional.getUserById(rs.getInt("professional_id"));
                professionals.add(professional);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (db != null) {
                try {
                    db.close();
                } catch (SQLException e) {
                    System.out.println("Database could not close");
                }
            }
        }
        return professionals;
    }

    public static MyLinkedList<Patient> getAllPatients() {
        MyLinkedList<Patient> patients = new MyLinkedList<>();
        Database db = null;
        try {
            db = new Database();
            String query = "SELECT patient_id FROM patient";
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                Patient patient = new Patient();
                patient.getUserById(rs.getInt("patient_id"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                try {
                    db.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return patients;
    }

    public static MySet<LocalTime> availableTime(LocalDate date, User user1, User user2) {
        MySet<LocalTime> user1AvailableTime = UserManager.availableTimeEachUser(date, user1);
        MySet<LocalTime> user2AvailableTime = UserManager.availableTimeEachUser(date, user2);
        MySet<LocalTime> availableTime = new MySet<>(user1AvailableTime);
        availableTime = availableTime.intersection(user2AvailableTime);
        return availableTime;
    }

    private static MySet<LocalTime> availableTimeEachUser(LocalDate date, User user) {
        Date dbDate = Date.valueOf(date);
        MySet<LocalTime> times = new MySet<>();
        for (int i = 9; i < 17; i++) {
            times.add(LocalTime.of(i, 0, 0));
        }
        Database db = null;
        try {
            db = new Database();
            String query = "";
            if (user instanceof Professional) {
                Professional professional = (Professional) user;
                query = String.format("SELECT start_time FROM appointment WHERE date = '%s' AND professional_id = '%s'", dbDate, professional.getProfessionalID());
            } else {
                Patient patient = (Patient) user;
                query = String.format("SELECT start_time FROM appointment WHERE date = '%s' AND patient_id = '%s'", dbDate, patient.getPatientID());

            }
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                times.remove(Time.valueOf(rs.getString("start_time")).toLocalTime());
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            try {
                db.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return times;
    }
}
