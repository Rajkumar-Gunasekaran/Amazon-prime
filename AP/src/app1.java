import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class app1 {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "159753";
    private static final String DATABASE_NAME = "amazon";
    private static final String TABLE_NAME = "users";

    public static void main(String[] args) {
        // Start the HTTP server
        startHttpServer();

        // Rest of your existing code
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Application!");

        System.out.print("Choose an option: \n1. Login\n2. Signup\nEnter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                scanner.nextLine();
                System.out.print("Enter your username: ");
                String loginUsername = scanner.nextLine();

                System.out.print("Enter your password: ");
                String loginPassword = scanner.nextLine();

                boolean loginSuccessful = login(loginUsername, loginPassword);

                if (loginSuccessful) {
                    System.out.println("Login successful!");
                    // Communicate with HTTP server
                    sendRequest("login?username=" + loginUsername + "&password=" + loginPassword);
                } else {
                    System.out.println("Login failed. Please check your credentials and try again.");
                }
                break;
            case 2:
                scanner.nextLine();
                System.out.print("Enter your username: ");
                String signupUsername = scanner.nextLine();

                System.out.print("Enter your email: ");
                String signupEmail = scanner.nextLine();

                System.out.print("Enter your password: ");
                String signupPassword = scanner.nextLine();

                boolean accountCreated = createAccount(signupUsername, signupEmail, signupPassword);

                if (accountCreated) {
                    System.out.println("Account created successfully!");
                    // Communicate with HTTP server
                    sendRequest("signup?username=" + signupUsername + "&email=" + signupEmail + "&password=" + signupPassword);
                } else {
                    System.out.println("Failed to create account. Please try again.");
                }
                break;
            default:
                System.out.println("Invalid choice. Exiting the application.");
        }

        // Close the scanner
        scanner.close();
    }

    private static boolean createAccount(String username, String email, String password) {
        try {
            try (Connection connection = DriverManager.getConnection(JDBC_URL + DATABASE_NAME, DB_USER, DB_PASSWORD)) {
                String sql = "INSERT INTO " + TABLE_NAME + " (Username, Email, Password) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, email);
                    preparedStatement.setString(3, password);

                    int rowsAffected = preparedStatement.executeUpdate();

                    return rowsAffected > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean login(String username, String password) {
        try {
            try (Connection connection = DriverManager.getConnection(JDBC_URL + DATABASE_NAME, DB_USER, DB_PASSWORD)) {
                String sql = "SELECT * FROM " + TABLE_NAME + " WHERE Username = ? AND Password = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        return resultSet.next();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void startHttpServer() {
        try {
            // Create an HTTP server on localhost and port 8000
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            // Set the context for handling requests
            server.createContext("/login", new LogHandler());
            server.createContext("/signup", new LogHandler());

            // Start the server
            server.start();
            System.out.println("HTTP Server started on port 8000");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class LogHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Retrieve input from the client
            String query = exchange.getRequestURI().getQuery();
            System.out.println("Received input from client: " + query);

            // Process the input (you can add your database logic here)

            // Send a response to the client
            String response = "Processed input successfully!";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private static void sendRequest(String query) {
        try {
            // Create a URL with the query
            URL url = new URL("http://localhost:8000/" + query);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Get the response code
            int responseCode = connection.getResponseCode();

            // Print the response code
            System.out.println("HTTP Server Response Code: " + responseCode);

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
