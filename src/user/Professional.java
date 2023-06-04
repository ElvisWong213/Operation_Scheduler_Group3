package user;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;
import type.Profession;

public class Professional extends User {
    private int professionalID;
    private String name;
    private Profession profession;
    private String workLocation;

    public Professional() {
        super();
        this.professionalID = 0;
        this.name = null;
        this.profession = null;
        this.workLocation = null;
    }

    public Professional(String email, String password, String name, Profession profession, String workLocation) {
        super(email, password);
        this.name = name;
        this.profession = profession;
        this.workLocation = workLocation;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfessionalID() {
        return professionalID;
    }

    public void setProfessionalID(int professionalID) {
        this.professionalID = professionalID;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public Profession getProfession() {
        return profession;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    @Override
    public boolean performLogin(String email, String password) {
        try {
            getUserByEmailPassword(email, password);
            String query = String.format("SELECT * FROM professional WHERE user_id = %2d;", userID);
            getDataFromDatabase(query);
        } catch (SQLException e) {
            return false;
        }
        return getLoginState();
    }

    @Override
    public void getUserById(int id) throws SQLException {
        String query = String.format("SELECT * FROM professional WHERE professional_id = %d;", id);
        getDataFromDatabase(query);
        super.getUserById(userID);
    }

    private void getDataFromDatabase(String query) throws SQLException {
        Database db = new Database();
        ResultSet rs = db.executeQuery(query);
        if (rs.next()) {
            this.userID = rs.getInt("user_id");
            this.professionalID = rs.getInt("professional_id");
            this.name = rs.getString("name");
            this.profession = Profession.valueOf(rs.getString("profession"));
            this.workLocation = rs.getString("work_location");
        }
        rs.close();
        db.close();
    }

    @Override
    public void clear() {
        super.clear();
        this.professionalID = 0;
        this.name = null;
        this.profession = null;
        this.workLocation = null;
    }

    @Override
    public boolean getLoginState() {
        return professionalID != 0;
    }

    public String getFullInfo() {
        StringBuilder output = new StringBuilder();

        output.append("Doctors Information:\n");
        output.append("Name: " + name + "\n");
        output.append("Specialization: " + profession + "\n");
        output.append("Work Location: " + workLocation + "\n");
        output.append("Email: " + email);
        return output.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Professional) {
            Professional professionObj = (Professional) obj;
            if (userID == professionObj.userID) {
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
                "INSERT INTO professional (user_id, name, profession, work_location) VALUES ('%s', '%s', '%s', '%s');",
                userID, name, profession, workLocation);
        db.executeUpdate(query);
    }

    @Override
    public void removeUser(int id) throws SQLException {
        getUserById(id);
        Database db = new Database();
        String query = String.format("DELETE FROM professional WHERE professional_id = %d;", professionalID);
        db.executeUpdate(query);
        super.removeUser(userID);
    }

    @Override
    public void editUser() throws SQLException {
        Database db = new Database();
        String query = String.format(
                "UPDATE professional SET name = '%s', profession = '%s', work_location = '%s' WHERE professional_id = %d;",
                name, profession, workLocation, professionalID);
        db.executeUpdate(query);
        super.editUser();
    }

}
