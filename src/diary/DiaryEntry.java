package diary;
import java.sql.Date;
import java.sql.Time;

import type.TreatmentType;
import user.Patient;

public class DiaryEntry {
    private int id;
    private Date date;
    private Time startTime;
    private Time endTime;
    private TreatmentType treatmentType;
    private Patient patient;

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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
