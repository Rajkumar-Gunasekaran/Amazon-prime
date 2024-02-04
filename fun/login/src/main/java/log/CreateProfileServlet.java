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

@WebServlet("/CreateProfileServlet")
public class CreateProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amz", "root", "159753");

            String username = request.getParameter("txtName");
            String password = request.getParameter("txtPwd");
            String fullName = request.getParameter("txtFullName");

            PreparedStatement ps = con.prepareStatement("INSERT INTO user_profiles (username, password, full_name) VALUES (?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, fullName);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                out.println("<font color = green size = 18 >Profile Created Successfully!<br>");
                out.print("<a href = login.jsp > Login </a>");
            } else {
                out.println("<font color = red size = 18 >Profile Creation Failed!<br>");
                out.print("<a href = createProfile.jsp > Try Again </a>");
            }

            ps.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
