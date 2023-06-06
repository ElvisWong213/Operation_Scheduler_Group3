package database;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private String url;
    private Connection connection;

    public Database() throws SQLException {
        url = "src" + File.separator + "database" + File.separator + "operation_scheduler.db";
        String jarPath;
        File file = new File(url);
        if (!file.exists()) {
            try {
                jarPath = Database.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                String databaseFolderPath = new File(jarPath).getParent();
                url = databaseFolderPath + File.separator + "operation_scheduler.db";
            } catch (URISyntaxException f) {
                // TODO Auto-generated catch block
                f.printStackTrace();
            }
        }
        url = "jdbc:sqlite:" + url;
        this.connection = DriverManager.getConnection(url);
        this.connection.setAutoCommit(false);
        Statement stmt = connection.createStatement();
        stmt.execute("PRAGMA foreign_keys = ON;");
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
