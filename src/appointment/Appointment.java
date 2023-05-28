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
        try {
            Date startDate = Date.valueOf(inputDate);
            Date endDate = Date.valueOf(inputDate.plusDays(7));
            Database db = new Database();
            String query = String.format("SELECT * FROM appointment WHERE (professional_id = %d OR patient_id = %d) AND date >= '%s' AND date <= '%s' ORDER BY date ASC, start_time ASC;", professionalID, patientID, startDate, endDate);
            ResultSet rs = db.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("appointment_id");
                Date date = rs.getDate("date");
                Time startTime = rs.getTime("start_time");
                Time endTime = rs.getTime("end_time");
                TreatmentType treatmentType = TreatmentType.valueOf(rs.getString("treatment_type"));
                int proID = rs.getInt("professional_id");
                int patID = rs.getInt("patient_id");

                AppointmentEntry appointmentEntry = new AppointmentEntry(id, date, startTime, endTime, treatmentType, proID, patID);
                appointments.add(appointmentEntry);
            }
            db.close();
        } catch (SQLException e) {
            System.out.println("Fail connect to database");
            e.printStackTrace();
        }
    }

    public void bookAppointment(AppointmentEntry ae) {
        try {
            Date dbDate = ae.getDate();
            Time dbStarTime = ae.getStartTime();
            Time dbEndTime = ae.getEndTime();
            TreatmentType treatmentType = ae.getTreatmentType();
            int professionalID = ae.getProfessionalID();
            int patientID = ae.getPatientID();
            Database db = new Database();
            String query = String.format("INSERT INTO appointment (date, start_time, end_time, treatment_type, professional_id, patient_id) VALUES ('%s', '%s', '%s', '%s', %d, %d)", dbDate, dbStarTime, dbEndTime, treatmentType, professionalID, patientID);
            db.executeUpdate(query);
            db.close();
        } catch (SQLException e) {
            System.out.println("Fail to book an appointment");
        }
    }

    public void editAppointment(AppointmentEntry ae) {
        try {
            int appointmentID = ae.getId();
            Date dbDate = ae.getDate();
            Time dbStarTime = ae.getStartTime();
            Time dbEndTime = ae.getEndTime();
            TreatmentType treatmentType = ae.getTreatmentType();
            int professionalID = ae.getProfessionalID();
            int patientID = ae.getPatientID();
            Database db = new Database();
            String query = String.format("UPDATE appointment SET date = '%s', start_time = '%s', end_time = '%s', treatment_type = '%s', professional_id = %d, patient_id = %d WHERE appointment_id = %d;", dbDate, dbStarTime, dbEndTime, treatmentType, professionalID, patientID, appointmentID);
            db.executeUpdate(query);
            db.close();
        } catch (SQLException e) {
            System.out.println("Fail to book an appointment");
        }
    }

    public void removeAppointment(AppointmentEntry ae) {
        try {
            int appointmentID = ae.getId();
            Database db = new Database();
            String query = String.format("DELETE FROM appointment WHERE appointment_id = %d;", appointmentID);
            db.executeUpdate(query);
            db.close();
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
