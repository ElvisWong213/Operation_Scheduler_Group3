package user;

import java.sql.*;
import java.time.*;

import dataStructure.*;
import database.Database;

public class UserManager {
    public static MyLinkedList<Professional> getAllProfessionals() {
        MyLinkedList<Professional> professionals = new MyLinkedList<>();
        try {
            Database db = new Database();
            String query = "SELECT professional_id FROM professional";
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                Professional professional = new Professional();
                professional.getUserById(rs.getInt("professional_id"));
                professionals.add(professional);
            }
        } catch (SQLException e) {
            // TODO: handle exception
        }
        return professionals;
    }

    public static MyLinkedList<Patient> getAllPatients() {
        MyLinkedList<Patient> patients = new MyLinkedList<>();
        try {
            Database db = new Database();
            String query = "SELECT patient_id FROM patient";
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                Patient patient = new Patient();
                patient.getUserById(rs.getInt("patient_id"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            // TODO: handle exception
        }
        return patients;
    }

    public static MySet<LocalTime> availableTime(LocalDate date, User user) {
        Date dbDate = Date.valueOf(date);
        MySet<LocalTime> times = new MySet<>();
        for (int i = 9; i < 17; i++) {
            times.add(LocalTime.of(i, 0, 0));
        }

        try {
            Database db = new Database();
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
                times.remove(rs.getTime("start_time").toLocalTime());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return times;
    }

}
