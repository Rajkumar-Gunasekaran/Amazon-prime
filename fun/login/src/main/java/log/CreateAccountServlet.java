package log;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CreateAccountServlet.class.getName());

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amz", "root", "159753");

            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");

            // Use a secure hashing algorithm for passwords in production
            PreparedStatement ps = con.prepareStatement("INSERT INTO login (uname, password) VALUES (?, ?)");
            ps.setString(1, username);
            ps.setString(2, password);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                out.println("<font color='green' size='18'>Account created successfully!<br>");
                out.print("<a href='login.jsp'>Login</a>");
            } else {
                out.println("<font color='red' size='18'>Account creation failed!<br>");
                out.print("<a href='createAccount.jsp'>Try AGAIN!</a>");
            }

            // Close resources
            ps.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Error in CreateAccountServlet", e);
            // Log or handle exceptions appropriately in a production environment
        }
    }
}
