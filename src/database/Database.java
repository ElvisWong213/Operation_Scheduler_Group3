package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static String url = "jdbc:mysql://localhost:3306/operation_scheduler";
    private static String user = "javaDB";
    private static String password = "P@ssw0rd";
    private Connection connection;

    public Database() throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    public int executeUpdate(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeUpdate(query);
    }

    public void close() throws SQLException {
        connection.close();
    }
}
