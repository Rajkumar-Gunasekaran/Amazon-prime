<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>Reset Password</title>
</head>
<body>
   <div align="center">
      <h1>Reset Password</h1>
   </div>
   <form action="ResetPasswordServlet" method="post">
      <label for="txtUsername">Username:</label>
      <input type="text" id="txtUsername" name="txtUsername" required><br>

      <label for="txtNewPassword">New Password:</label>
      <input type="password" id="txtNewPassword" name="txtNewPassword" required><br>

      <label for="txtRetypeNewPassword">Retype New Password:</label>
      <input type="password" id="txtRetypeNewPassword" name="txtRetypeNewPassword" required><br>

      <input type="submit" value="Reset Password">
   </form>
</body>
</html>
