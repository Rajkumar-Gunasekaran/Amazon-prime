import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class App {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "159753";
    private static final String DATABASE_NAME = "amazon";
    private static final String TABLE_NAME = "users";

    public static void main(String[] args) {
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
                } else {
                    System.out.println("Failed to create account. Please try again.");
                }
                break;
            default:
                System.out.println("Invalid choice. Exiting the application.");
        }

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
}
