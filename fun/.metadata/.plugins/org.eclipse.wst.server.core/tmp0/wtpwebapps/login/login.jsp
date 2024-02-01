<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>Login</title>
</head>
<body>
   <div align="center">
      <h1>User Login</h1>
   </div>
   <form action="LoginServlet" method="post">
      <!-- Your existing login form fields go here -->
      <table>
         <tr>
            <td>Enter Name:</td>
            <td><input type="text" name="txtName"></td>
         </tr>
         <tr>
            <td>Enter Password:</td>
            <td><input type="password" name="txtPwd"></td>
         </tr>
         <tr>
            <td><input type="submit" value="Login"></td>
            <td><input type="reset"></td>
         </tr>
      </table>
   </form>

   <!-- Add a link to navigate to reset password page -->
   <div align="center">
      <a href="resetPassword.jsp">Forgot Password? Reset it here</a>
   </div>
</body>
</html>
