package gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Appointment {
    Patient patient;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String description;
    private List<String> doctors;
    private String notes;

    public Appointment() {

    }

    public Appointment(String year, String month, String day, String hour, String minute, Patient patient, String description) {
        this.year = Integer.parseInt(year);
        this.month = Integer.parseInt(month);
        this.day = Integer.parseInt(day);
        this.hour = Integer.parseInt(hour);
        this.minute = Integer.parseInt(minute);
        this.patient = patient;
        this.description = description;
        this.doctors = new ArrayList<>();
        this.notes = "";
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<String> doctors) {
        this.doctors = doctors;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getDate() {
        return year + "-" + month + "-" + day + " " + hour + ":" + minute;
    }
}
