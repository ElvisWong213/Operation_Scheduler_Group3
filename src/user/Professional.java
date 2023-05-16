package user;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseInterface;

public class Professional extends User {
    private int professionalID;
    private String name;
    private String profession;
    private String workLocation;

    public Professional() {
        super();
        this.professionalID = 0;
        this.name = null;
        this.profession = null;
        this.workLocation = null;
    }

    public int getProfessionalID() {
        return professionalID;
    }

    public void setProfessionalID(int professionalID) {
        this.professionalID = professionalID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getProfession() {
        return profession;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    @Override
    public boolean performLogin(String email, String password) {
        try {
            retrieveData(email, password);
            DatabaseInterface db = new DatabaseInterface();
            String query = String.format("SELECT * FROM professional WHERE user_id = %2d;", userID);
            ResultSet rs = db.executeQuery(query);
            if (rs.next()) {
                professionalID = rs.getInt("professional_id");
                name = rs.getString("name");
                profession = rs.getString("profession");
                workLocation = rs.getString("work_location");
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
