<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>User Profile</title>
</head>
<body>
   <div align="center">
      <h1>User Profile</h1>
   </div>
   <form action="UserProfileServlet" method="post">
      <table>
         <tr>
            <td>Enter Username:</td>
            <td><input type="text" name="txtName"></td>
         </tr>
         <tr>
            <td><input type="submit" value="View Profile"></td>
         </tr>
      </table>
   </form>
</body>
</html>
