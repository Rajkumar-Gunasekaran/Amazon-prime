import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class prime {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "159753";
    private static final String DATABASE_NAME = "amazon";
    private static final String TABLE_NAME = "users";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Account Creation!");

        createDatabaseAndTable();

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        boolean accountCreated = createAccount(username, password);

        if (accountCreated) {
            System.out.println("Account created successfully!");
        } else {
            System.out.println("Failed to create account. Please try again.");
        }

        scanner.close();
    }

    private static void createDatabaseAndTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {

                if (!databaseExists(connection, DATABASE_NAME)) {

                    createDatabase(connection, DATABASE_NAME);
                }

                try (Statement statement = connection.createStatement()) {
                    statement.execute("USE " + DATABASE_NAME);

                    if (!tableExists(connection, TABLE_NAME)) {

                        createTable(connection, TABLE_NAME);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean databaseExists(Connection connection, String databaseName) throws SQLException {
        ResultSet resultSet = connection.getMetaData().getCatalogs();

        while (resultSet.next()) {
            if (resultSet.getString(1).equalsIgnoreCase(databaseName)) {
                return true;
            }
        }

        return false;
    }

    private static void createDatabase(Connection connection, String databaseName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE " + databaseName);
        }
    }

    private static boolean tableExists(Connection connection, String tableName) throws SQLException {
        ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null);

        return resultSet.next();
    }

    private static void createTable(Connection connection, String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE " + tableName + " ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "username VARCHAR(255) NOT NULL,"
                    + "password VARCHAR(255) NOT NULL)";
            statement.executeUpdate(createTableSQL);
        }
    }

    private static boolean createAccount(String username, String password) {
        try {

            try (Connection connection = DriverManager.getConnection(JDBC_URL + DATABASE_NAME, DB_USER, DB_PASSWORD)) {

                String sql = "INSERT INTO " + TABLE_NAME + " (username, password) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

                    int rowsAffected = preparedStatement.executeUpdate();

                    return rowsAffected > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
