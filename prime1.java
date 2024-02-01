import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class prime1 {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "159753";
    private static final String DATABASE_NAME = "amazon";
    private static final String TABLE_NAME = "users";

    public static void main(String[] args) throws IOException {
        // Start the HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new MyHandler());
        server.createContext("/createAccount", new CreateAccountHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Server is listening on port 8080");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "<html><body>"
                    + "<h1>Welcome to Account Creation!</h1>"
                    + "<form action=\"/createAccount\" method=\"post\">"
                    + "  <label for=\"username\">Enter your username:</label>"
                    + "  <input type=\"text\" id=\"username\" name=\"username\"><br>"
                    + "  <label for=\"password\">Enter your password:</label>"
                    + "  <input type=\"password\" id=\"password\" name=\"password\"><br>"
                    + "  <input type=\"submit\" value=\"Create Account\">"
                    + "</form>"
                    + "</body></html>";

            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class CreateAccountHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Get the form data from the request
                String formData = new String(exchange.getRequestBody().readAllBytes(), "UTF-8");
                String[] params = formData.split("&");
                String username = params[0].split("=")[1];
                String password = params[1].split("=")[1];

                // Create the account
                boolean accountCreated = createAccount(username, password);

                // Send response back to the client
                String response = accountCreated ? "Account created successfully!" : "Failed to create account. Please try again.";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
                exchange.close();
            }
        }
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

}
