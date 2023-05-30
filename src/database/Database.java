package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private final String url = "jdbc:sqlite:src/database/operation_scheduler.db";
    private Connection connection;

    public Database() throws SQLException {
        this.connection = DriverManager.getConnection(url);
        this.connection.setAutoCommit(false);
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(query);
        return resultSet;
    }

    public int executeUpdate(String query) throws SQLException {
        Statement stmt;
        int updateRow = 0;
        try {
            stmt = connection.createStatement();
            updateRow = stmt.executeUpdate(query);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
        return updateRow;
    }

    public void close() throws SQLException {
        connection.close();
    }
}
