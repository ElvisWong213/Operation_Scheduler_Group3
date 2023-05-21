package user;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import database.Database;

public class UserManager {
    public static ArrayList<Professional> getAllProfessionals() {
        ArrayList<Professional> professionals = new ArrayList<>();
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

    public static ArrayList<Patient> getAllPatients() {
        ArrayList<Patient> patients = new ArrayList<>();
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

    public static Set<LocalTime> availableTime(LocalDate date, User user) {
        Date dbDate = Date.valueOf(date);
        Set<LocalTime> times = new TreeSet<>();
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
