package user;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseInterface;

abstract class User {
    protected int userID;
    protected String email;
    protected String password;
    protected boolean isLogin;

    protected User() {
        this.userID = 0;
        this.email = null;
        this.password = null;
        this.isLogin = false;
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

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public abstract boolean performLogin(String email, String password);

    protected void retrieveData(String email, String password) throws SQLException {
        DatabaseInterface db = new DatabaseInterface();
        String query = String.format("SELECT * FROM user WHERE email = '%s' AND password = '%s';", email, password);
        ResultSet rs = db.executeQuery(query);
        if (rs.next()) {
            this.userID = rs.getInt("user_id");
            this.email = rs.getString("email");
            this.password = rs.getString("password");
        }
        db.close();
    }

    public boolean getLoginState() {
        return isLogin;
    }

}
