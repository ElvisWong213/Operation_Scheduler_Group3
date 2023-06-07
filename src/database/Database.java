package database;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private String sqlUrl;
    private Connection connection;

    public Database() throws SQLException {
        String jarPath;
        String path = "";
        try {
            jarPath = Database.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            String databaseFolderPath = new File(jarPath).getParent();
            path = databaseFolderPath + File.separator + "operation_scheduler.db";
            sqlUrl = "jdbc:sqlite:" + path;
        } catch (URISyntaxException f) {
            // TODO Auto-generated catch block
            f.printStackTrace();
        }
        File file = new File(path);
        System.out.println(file.exists());
        if (!file.exists()) {
            createNewDatabase();
        }

        this.connection = DriverManager.getConnection(sqlUrl);
        this.connection.setAutoCommit(false);
        this.connection.setReadOnly(false);
        Statement stmt = connection.createStatement();
        stmt.execute("PRAGMA foreign_keys = ON;");
        stmt.close();
    }

    private void createNewDatabase() {
        StringBuilder sqlStatements = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("sqlite_database_initialization.sql")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sqlStatements.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("failed to read SQL file");
        }
        Statement statementInit = null;
        Connection newConnection  = null;
        try {
            newConnection = DriverManager.getConnection("dbc:sqlite:C:\\Users\\steve\\Documents\\Work\\Operation_Scheduler_Group3\\operation_scheduler.db");
            String[] statements = sqlStatements.toString().split(";");
            statementInit = newConnection.createStatement();
            for (String sql : statements) {
                if (!sql.trim().isEmpty()) {
                    statementInit.executeUpdate(sql);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statementInit != null) {
                try {
                    statementInit.close();
                } catch (SQLException e) {
                    // TODO: handle exception
                }
            }
            if (newConnection != null) {
                try {
                    newConnection.close();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }

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
