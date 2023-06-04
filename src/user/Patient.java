package user;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import database.Database;
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

    public Patient(String email, String password, String name, Gender gender, Date dateOfBirth, String phoneNumber,
            String address) {
        super(email, password);
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
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
            getUserByEmailPassword(email, password);
            String query = String.format("SELECT * FROM patient WHERE user_id = %2d;", userID);
            getDataFromDatabase(query);
        } catch (SQLException e) {
            return false;
        }
        return getLoginState();
    }

    @Override
    public void getUserById(int id) throws SQLException {
        String query = String.format("SELECT * FROM patient WHERE patient_id = %d;", id);
        getDataFromDatabase(query);
        super.getUserById(userID);
    }

    private void getDataFromDatabase(String query) throws SQLException {
        Database db = new Database();
        ResultSet rs = db.executeQuery(query);
        if (rs.next()) {
            userID = rs.getInt("user_id");
            patientID = rs.getInt("patient_id");
            name = rs.getString("name");
            gender = Gender.valueOf(rs.getString("gender"));
            dateOfBirth = Date.valueOf(rs.getString("date_of_birth"));
            phoneNumber = rs.getString("phone_number");
            address = rs.getString("address");
        }
        rs.close();
        db.close();
    }

    @Override
    public void clear() {
        super.clear();
        this.patientID = 0;
        this.name = null;
        this.gender = null;
        this.dateOfBirth = null;
        this.phoneNumber = null;
        this.address = null;
    }

    @Override
    public boolean getLoginState() {
        return patientID != 0;
    }

    public String getFullInfo() {
        StringBuilder output = new StringBuilder();

        int age = (LocalDate.now().minusYears(dateOfBirth.toLocalDate().getYear())).getYear();

        output.append("Patient Information:\n");
        output.append("Name: " + name + "\n");
        output.append("Age: " + age + "\n");
        output.append("Phone: " + phoneNumber + "\n");
        output.append("Address: " + address);
        return output.toString();
    }

    public String getInfo() {
        StringBuilder output = new StringBuilder();

        int age = (LocalDate.now().minusYears(dateOfBirth.toLocalDate().getYear())).getYear();

        output.append("Name: " + name + "\n");
        output.append("Age: " + age + "\n");
        return output.toString();
    }
    

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Patient) {
            Patient patientObj = (Patient) obj;
            if (userID == patientObj.userID) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addUser() throws SQLException {
        super.addUser();
        getUserByEmailPassword(email, password);
        Database db = new Database();
        String query = String.format(
                "INSERT INTO patient (user_id, name, gender, date_of_birth, phone_number, address) VALUES ('%d', '%s', '%s', '%s', '%s', '%s');",
                userID, name, gender, dateOfBirth, phoneNumber, address);
        db.executeUpdate(query);
    }

    @Override
    public void removeUser(int id) throws SQLException {
        getUserById(id);
        Database db = new Database();
        String query = String.format("DELETE FROM patient WHERE patient_id = %d;", patientID);
        db.executeUpdate(query);
        super.removeUser(userID);
    }

    @Override
    public void editUser() throws SQLException {
        Database db = new Database();
        String query = String.format(
                "UPDATE patient SET name = '%s', gender = '%s', date_of_birth = '%s', phone_number = '%s', address = '%s' WHERE patient_id = '%d';",
                name, gender, dateOfBirth, phoneNumber, address, patientID);
        db.executeUpdate(query);
        super.editUser();
    }

}
