package appointment;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import type.TreatmentType;
import user.Patient;
import user.Professional;

public class AppointmentEntry {
    private int id;
    private Date date;
    private Time startTime;
    private Time endTime;
    private TreatmentType treatmentType;
    private int professionalID;
    private int patientID;
    
    public AppointmentEntry(int id, Date date, Time startTime, Time endTime, TreatmentType treatmentType,
            int professionalID, int patientID) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.treatmentType = treatmentType;
        this.professionalID = professionalID;
        this.patientID = patientID;
    }

    public AppointmentEntry(Date date, Time startTime, Time endTime, TreatmentType treatmentType,
            int professionalID, int patientID) {
        this.id = 0;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.treatmentType = treatmentType;
        this.professionalID = professionalID;
        this.patientID = patientID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public TreatmentType getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(TreatmentType treatmentType) {
        this.treatmentType = treatmentType;
    }

    public int getProfessionalID() {
        return professionalID;
    }

    public void setProfessionalID(int professionalID) {
        this.professionalID = professionalID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }
    
    public void print() {
        System.out.println("Date: " + date + " " + startTime + " - " + endTime);
        System.out.println("Treatment type:  " + treatmentType);
        System.out.println("Patient name: " + getPatientName(patientID));
        System.out.println("Staff name: " + getProfessionalName(professionalID));
    }
    
    private String getProfessionalName(int id) {
        Professional professional = new Professional();
        try {
            professional.getUserById(id);
        } catch (SQLException e) {
            return null;
        }
        return professional.getName();
    }

    private String getPatientName(int id) {
        Patient patient = new Patient();
        try {
            patient.getUserById(id);
        } catch (SQLException e) {
            return null;
        }
        return patient.getName();
    }
}
