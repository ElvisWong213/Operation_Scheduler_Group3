package backend.diary;

import java.sql.*;
import java.time.*;

import org.json.JSONObject;

public class DiaryEntry implements Comparable<DiaryEntry> {
    private int id;
    private Date date;
    private Time time;
    private String note;
    private int professionalID;
    private int patientID;

    public DiaryEntry() {}
    
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

    public DiaryEntry(JSONObject jsonObject) {
        this.id = jsonObject.getInt("id");
        this.date = Date.valueOf(jsonObject.getString("date"));
        this.time = Time.valueOf(jsonObject.getString("time"));
        this.note = jsonObject.getString("note");
        this.professionalID = jsonObject.getInt("professional_id");
        this.patientID = jsonObject.getInt("patient_id");
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

        int compare = 0;
        if (other.date != null && other.time != null) {
            LocalDate otherLd = other.date.toLocalDate();
            LocalTime otherLt = other.time.toLocalTime();
            LocalDateTime otherLdt = LocalDateTime.of(otherLd, otherLt);
            compare = ldt.compareTo(otherLdt);
        }
        if (compare == 0) {
            if (patientID > other.patientID) {
                compare = 1;
            } else if (patientID < other.patientID) {
                compare = -1;
            } else {
                compare = 0;
            }
        }
        return compare;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("date", date);
        jsonObject.put("time", time);
        jsonObject.put("note", note);
        jsonObject.put("professional_id", professionalID);
        jsonObject.put("patient_id", patientID);
        return jsonObject;
    }

    public void copyOf(DiaryEntry newDiaryEntry) {
        this.id = newDiaryEntry.getId();
        this.date = newDiaryEntry.getDate();
        this.time = newDiaryEntry.getTime();
        this.note = newDiaryEntry.getNote();
        this.professionalID = newDiaryEntry.getProfessionalID();
        this.patientID = newDiaryEntry.getPatientID();
    }
    
}
