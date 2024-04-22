package com.amazon.demo.controller;

import com.amazon.demo.model.User;
import com.amazon.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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

        if (user.isPresent() && user.get().getPassword().equals(password)) {
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
}
