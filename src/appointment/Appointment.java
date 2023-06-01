package appointment;
import java.sql.*;
import java.time.*;

import dataStructure.MyLinkedList;
import database.Database;
import type.TreatmentType;

public class Appointment {
    private MyLinkedList<AppointmentEntry> appointments;

    public Appointment() {
        this.appointments = new MyLinkedList<>();
    }

    public MyLinkedList<AppointmentEntry> getAppointments() {
        return appointments;
    }

    public void setAppointments(MyLinkedList<AppointmentEntry> appointments) {
        this.appointments = appointments;
    }

    public void searchAppointmentsInWeek(LocalDate inputDate, int professionalID, int patientID) {
        appointments.clear();
        Database db = null;
        try {
            Date startDate = Date.valueOf(inputDate);
            Date endDate = Date.valueOf(inputDate.plusDays(7));
            db = new Database();
            String query = String.format("SELECT * FROM appointment WHERE (professional_id = %d OR patient_id = %d) AND date >= '%s' AND date <= '%s' ORDER BY date ASC, start_time ASC;", professionalID, patientID, startDate, endDate);
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("appointment_id");
                Date date = Date.valueOf(rs.getString("date"));
                Time startTime = Time.valueOf(rs.getString("start_time"));
                Time endTime = Time.valueOf(rs.getString("end_time"));
                TreatmentType treatmentType = TreatmentType.valueOf(rs.getString("treatment_type"));
                String description = rs.getString("description");
                int proID = rs.getInt("professional_id");
                int patID = rs.getInt("patient_id");

                AppointmentEntry appointmentEntry = new AppointmentEntry(id, date, startTime, endTime, treatmentType, description, proID, patID);
                appointments.add(appointmentEntry);
            }
        } catch (SQLException e) {
            System.out.println("Fail connect to database");
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
    }

    public void getAllAppointments(int professionalID, int patientID) {
        appointments.clear();
        Database db = null;
        try {
            db = new Database();
            String query = String.format("SELECT * FROM appointment WHERE (professional_id = %d OR patient_id = %d) ORDER BY date ASC, start_time ASC;", professionalID, patientID);
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("appointment_id");
                Date date = Date.valueOf(rs.getString("date"));
                Time startTime = Time.valueOf(rs.getString("start_time"));
                Time endTime = Time.valueOf(rs.getString("end_time"));
                TreatmentType treatmentType = TreatmentType.valueOf(rs.getString("treatment_type"));
                String description = rs.getString("description");
                int proID = rs.getInt("professional_id");
                int patID = rs.getInt("patient_id");

                AppointmentEntry appointmentEntry = new AppointmentEntry(id, date, startTime, endTime, treatmentType, description, proID, patID);
                appointments.add(appointmentEntry);
            }
        } catch (SQLException e) {
            System.out.println("Fail connect to database");
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
    }

    public static void bookAppointment(AppointmentEntry ae) {
        try {
            Date dbDate = ae.getDate();
            Time dbStarTime = ae.getStartTime();
            Time dbEndTime = ae.getEndTime();
            TreatmentType treatmentType = ae.getTreatmentType();
            String description = ae.getDescription();
            int professionalID = ae.getProfessionalID();
            int patientID = ae.getPatientID();
            Database db = new Database();
            String query = String.format("INSERT INTO appointment (date, start_time, end_time, treatment_type, description, professional_id, patient_id) VALUES ('%s', '%s', '%s', '%s', '%s', %d, %d)", dbDate, dbStarTime, dbEndTime, treatmentType, description, professionalID, patientID);
            db.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Fail to book an appointment");
        }
    }

    public static void editAppointment(AppointmentEntry ae) {
        try {
            int appointmentID = ae.getId();
            Date dbDate = ae.getDate();
            Time dbStarTime = ae.getStartTime();
            Time dbEndTime = ae.getEndTime();
            TreatmentType treatmentType = ae.getTreatmentType();
            String description = ae.getDescription();
            int professionalID = ae.getProfessionalID();
            int patientID = ae.getPatientID();
            Database db = new Database();
            String query = String.format("UPDATE appointment SET date = '%s', start_time = '%s', end_time = '%s', treatment_type = '%s', description = '%s', professional_id = %d, patient_id = %d WHERE appointment_id = %d;", dbDate, dbStarTime, dbEndTime, treatmentType, description, professionalID, patientID, appointmentID);
            db.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Fail to book an appointment");
        }
    }

    public static void removeAppointment(AppointmentEntry ae) {
        try {
            int appointmentID = ae.getId();
            Database db = new Database();
            String query = String.format("DELETE FROM appointment WHERE appointment_id = %d;", appointmentID);
            db.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Fail to book an appointment");
        }
    }

    public void printAllAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No Appointment");
        }
        int counter = 0;
        for (AppointmentEntry ae : appointments) {
            System.out.println("");
            System.out.println("Appointment: " + ++counter);
            ae.print();
        }
    }
}
