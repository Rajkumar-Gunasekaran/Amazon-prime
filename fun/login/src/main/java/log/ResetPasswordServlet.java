package log;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amz", "root", "159753");

            String username = request.getParameter("txtUsername");
            String newPassword = request.getParameter("txtNewPassword");
            String retypeNewPassword = request.getParameter("txtRetypeNewPassword");

            if (!newPassword.equals(retypeNewPassword)) {
                out.println("<font color='red' size='18'>New passwords do not match!<br>");
                out.print("<a href='resetPassword.jsp'>Try AGAIN!</a>");
            } else {
                // Use a secure hashing algorithm for passwords in production
                PreparedStatement ps = con.prepareStatement("UPDATE login SET password = ? WHERE uname = ?");
                ps.setString(1, newPassword);
                ps.setString(2, username);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    out.println("<font color='green' size='18'>Password reset successfully!<br>");
                    out.print("<a href='login.jsp'>Login</a>");
                } else {
                    out.println("<font color='red' size='18'>Password reset failed!<br>");
                    out.print("<a href='resetPassword.jsp'>Try AGAIN!</a>");
                }

                // Close resources
                ps.close();
            }

            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Log or handle exceptions appropriately in a production environment
        }
    }
}
