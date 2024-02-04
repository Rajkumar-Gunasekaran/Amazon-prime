<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>Create Profile</title>
</head>
<body>
   <div align="center">
      <h1>Create User Profile</h1>
   </div>
   <form action="CreateProfileServlet" method="post">
      <table>
         <tr>
            <td>Enter Username:</td>
            <td><input type="text" name="txtName"></td>
         </tr>
         <tr>
            <td>Enter Password:</td>
            <td><input type="password" name="txtPwd"></td>
         </tr>
         <tr>
            <td>Enter Full Name:</td>
            <td><input type="text" name="txtFullName"></td>
         </tr>
         <tr>
            <td><input type="submit" value="Create Profile"></td>
            <td><input type="reset"></td>
         </tr>
      </table>
   </form>
</body>
</html>
