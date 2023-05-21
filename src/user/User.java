package user;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;

public abstract class User {
    protected int userID;
    protected String name;
    protected String email;
    protected String password;

    protected User() {
        this.userID = 0;
        this.name = null;
        this.email = null;
        this.password = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int id) {
        this.userID = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract boolean getLoginState();

    public abstract boolean performLogin(String email, String password);

    protected void getUserByEmailPassword(String email, String password) throws SQLException {
        String query = String.format("SELECT * FROM user WHERE email = '%s' AND password = '%s';", email, password);
        getDataFromDatabase(query);
    }

    public void getUserById(int id) throws SQLException {
        String query = String.format("SELECT * FROM user WHERE user_id = %2d;", id);
        getDataFromDatabase(query);
    }

    private void getDataFromDatabase(String query) throws SQLException {
        Database db = new Database();
        ResultSet rs = db.executeQuery(query);
        if (rs.next()) {
            this.userID = rs.getInt("user_id");
            this.name = rs.getString("name");
            this.email = rs.getString("email");
            this.password = rs.getString("password");
        }
        rs.close();
        db.close();
    }

    public void clear() {
        this.userID = 0;
        this.name = null;
        this.email = null;
        this.password = null;
    }

}
