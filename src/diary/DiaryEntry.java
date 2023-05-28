package diary;

import java.sql.*;
import java.time.*;

public class DiaryEntry implements Comparable<DiaryEntry> {
    private int id;
    private Date date;
    private Time time;
    private String note;
    private int professionalID;
    private int patientID;
    
    public DiaryEntry(int id, Date date, Time time, String note, int professional_id, int patient_id) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.note = note;
        this.professionalID = professional_id;
        this.patientID = patient_id;
    }

    public DiaryEntry(Date date, Time time, String note, int professional_id, int patient_id) {
        this.id = 0;
        this.date = date;
        this.time = time;
        this.note = note;
        this.professionalID = professional_id;
        this.patientID = patient_id;
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
    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = time;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
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

    @Override
    public int compareTo(DiaryEntry other) {
        LocalDate ld = date.toLocalDate();
        LocalTime lt = time.toLocalTime();
        LocalDateTime ldt = LocalDateTime.of(ld, lt);

        LocalDate otherLd = other.date.toLocalDate();
        LocalTime otherLt = other.time.toLocalTime();
        LocalDateTime otherLdt = LocalDateTime.of(otherLd, otherLt);
        return ldt.compareTo(otherLdt);
    }

    
}
