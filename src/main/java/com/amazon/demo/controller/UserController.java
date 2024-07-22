package com.amazon.demo.controller;

import com.amazon.demo.model.User;
import com.amazon.demo.service.Base64Utility;
import com.amazon.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> processRegistration(@RequestParam("username") String username,
                                                      @RequestParam("email") String email,
                                                      @RequestParam("password") String password) {
        String encodedPassword = Base64Utility.encode(password);
        User user = new User(username, email, encodedPassword);
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> processLogin(@RequestParam("username") String username,
                                               @RequestParam("password") String password,
                                               HttpSession session) {
        Optional<User> user = userService.findByUsername(username);

        if (user.isPresent() && Base64Utility.decode(user.get().getPassword()).equals(password) && user.get().getType() == 1) {
            session.setAttribute("userType", 1);
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("username") String username,
                                                @RequestParam("oldPassword") String oldPassword,
                                                @RequestParam("newPassword") String newPassword) {
        Optional<User> user = userService.findByUsername(username);

        if (user.isPresent() && Base64Utility.decode(user.get().getPassword()).equals(oldPassword)) {
            User updatedUser = user.get();
            updatedUser.setPassword(Base64Utility.encode(newPassword));
            userService.saveUser(updatedUser);

            return ResponseEntity.ok("Password reset successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password reset failed");
        }
    }

    @PostMapping("/admin/register")
    public ResponseEntity<String> processAdminRegistration(@RequestParam("username") String username,
                                                           @RequestParam("email") String email,
                                                           @RequestParam("password") String password) {
        String encodedPassword = Base64Utility.encode(password);
        User adminUser = new User(username, email, encodedPassword);
        adminUser.setType(0); // Set user type as admin
        userService.saveUser(adminUser);
        return ResponseEntity.ok("Admin registered successfully");
    }

    @PostMapping("/admin/login")
    public ResponseEntity<String> processAdminLogin(@RequestParam("username") String username,
                                                    @RequestParam("password") String password,
                                                    HttpSession session) {
        Optional<User> adminUser = userService.findByUsername(username);

        if (adminUser.isPresent() && adminUser.get().getType() == 0 && Base64Utility.decode(adminUser.get().getPassword()).equals(password)) {
            session.setAttribute("userType", 0);
            return ResponseEntity.ok("Admin login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin login failed");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Logout successful");
    }
}
