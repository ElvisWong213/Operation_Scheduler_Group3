package user;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseInterface;
import type.Gender;

public class Patient extends User {
    private int patientID;
    private String name;
    private Gender gender;
    private Date dateOfBirth;
    private String phoneNumber;
    private String address;
    

    public Patient() {
        super();
        this.patientID = 0;
        this.name = null;
        this.gender = null;
        this.dateOfBirth = null;
        this.phoneNumber = null;
        this.address = null;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean performLogin(String email, String password) {
        try {
            retrieveData(email, password);
            DatabaseInterface db = new DatabaseInterface();
            String query = String.format("SELECT * FROM patient WHERE user_id = %2d;", userID);
            ResultSet patientRs = db.executeQuery(query);
            if (patientRs.next()) {
                patientID = patientRs.getInt("patient_id");
                name = patientRs.getString("name");
                gender = Gender.valueOf(patientRs.getString("gender"));
                dateOfBirth = patientRs.getDate("date_of_birth");
                phoneNumber = patientRs.getString("phone_number");
                address = patientRs.getString("address");
                isLogin = true;
                db.close();
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

}
