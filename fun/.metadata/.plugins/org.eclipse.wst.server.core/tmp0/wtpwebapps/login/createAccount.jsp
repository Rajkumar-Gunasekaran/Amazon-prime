<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>Create Account</title>

   <script>
      function validatePassword() {
         var password = document.getElementById("txtPassword").value;
         var retypePassword = document.getElementById("txtRetypePassword").value;

         if (password !== retypePassword) {
            alert("Passwords do not match!");
            return false;
         }

         return true;
      }
   </script>
</head>
<body>
   <div align="center">
      <h1>Create an Account</h1>
   </div>
   <form action="CreateAccountServlet" method="post" onsubmit="return validatePassword();">
      <label for="txtUsername">Username:</label>
      <input type="text" id="txtUsername" name="txtUsername" required><br>

      <label for="txtPassword">Password:</label>
      <input type="password" id="txtPassword" name="txtPassword" required><br>

      <label for="txtRetypePassword">Retype Password:</label>
      <input type="password" id="txtRetypePassword" name="txtRetypePassword" required><br>

      <input type="submit" value="Create Account">
   </form>
</body>
</html>
