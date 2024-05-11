package com.amazon.demo.controller;

import com.amazon.demo.model.User;
import com.amazon.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> processRegistration(@RequestParam("username") String username,
                                                      @RequestParam("email") String email,
                                                      @RequestParam("password") String password) {
        User user = new User(username, email, password);
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> processLogin(@RequestParam("username") String username,
                                               @RequestParam("password") String password) {

        Optional<User> user = userService.findByUsername(username);

        if (user.isPresent() && user.get().getPassword().equals(password) && user.get().getType() == 1) {
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

        if (user.isPresent() && user.get().getPassword().equals(oldPassword)) {
            User updatedUser = user.get();
            updatedUser.setPassword(newPassword);
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
        User adminUser = new User(username, email, password);
        adminUser.setType(0); // Set user type as admin
        userService.saveUser(adminUser);
        return ResponseEntity.ok("Admin registered successfully");
    }

    @PostMapping("/admin/login")
    public ResponseEntity<String> processAdminLogin(@RequestParam("username") String username,
                                                    @RequestParam("password") String password) {

        Optional<User> adminUser = userService.findByUsername(username);

        if (adminUser.isPresent() && adminUser.get().getType() == 0 && adminUser.get().getPassword().equals(password)) {
            return ResponseEntity.ok("Admin login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin login failed");
        }
    }
}
