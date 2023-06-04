package user;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;

public abstract class User {
    protected int userID;
    protected String email;
    protected String password;

    protected User() {
        this.userID = 0;
        this.email = null;
        this.password = null;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
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

    public abstract String getName();

    public abstract boolean getLoginState();

    public abstract boolean performLogin(String email, String password);

    public void addUser() throws SQLException {
        Database db = new Database();
        String query = String.format("INSERT INTO user (email, password) VALUES ('%s', '%s');", email, password);
        db.executeUpdate(query);
    }

    public void removeUser(int id) throws SQLException {
        getUserById(id);
        Database db = new Database();
        String query = String.format("DELETE FROM user WHERE user_id = %d;", userID);
        db.executeUpdate(query);
    }

    public void editUser() throws SQLException {
        Database db = new Database();
        String query = String.format("UPDATE user SET email = '%s', password = '%s' WHERE user_id = %d;", email,
                password, userID);
        db.executeUpdate(query);
    }

    protected void getUserByEmailPassword(String email, String password) throws SQLException {
        String query = String.format("SELECT * FROM user WHERE email = '%s' AND password = '%s';", email, password);
        getUserDataFromDatabase(query);
    }

    public void getUserById(int id) throws SQLException {
        String query = String.format("SELECT * FROM user WHERE user_id = %d;", id);
        getUserDataFromDatabase(query);
    }

    private void getUserDataFromDatabase(String query) throws SQLException {
        Database db = new Database();
        ResultSet rs = db.executeQuery(query);
        if (rs.next()) {
            this.userID = rs.getInt("user_id");
            this.email = rs.getString("email");
            this.password = rs.getString("password");
        }
        rs.close();
        db.close();
    }

    public void clear() {
        this.userID = 0;
        this.email = null;
        this.password = null;
    }

}
