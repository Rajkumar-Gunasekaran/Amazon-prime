package com.amazon.demo.controller;

import com.amazon.demo.model.UserProfile;
import com.amazon.demo.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user-profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    public ResponseEntity<String> createUserProfile(@RequestParam Long userId,
                                                    @RequestParam String firstName,
                                                    @RequestParam String lastName,
                                                    @RequestParam String profilePicture,
                                                    @RequestParam String preferences,
                                                    HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userType = (Integer) session.getAttribute("userType");

        if (userType == null || userType != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserProfile userProfile = new UserProfile(userId, firstName, lastName, profilePicture, preferences);
        userProfile.setCreatedAt(LocalDateTime.now());
        userProfile.setUpdatedAt(LocalDateTime.now());
        userProfileService.createUserProfile(userProfile);
        return ResponseEntity.ok("User profile created successfully");
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<String> updateUserProfile(@PathVariable Long profileId,
                                                    @RequestParam String firstName,
                                                    @RequestParam String lastName,
                                                    @RequestParam String profilePicture,
                                                    @RequestParam String preferences,
                                                    HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userType = (Integer) session.getAttribute("userType");

        if (userType == null || userType != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserProfile updatedProfile = new UserProfile(null, firstName, lastName, profilePicture, preferences);
        updatedProfile.setUpdatedAt(LocalDateTime.now());
        userProfileService.updateUserProfile(profileId, updatedProfile);
        return ResponseEntity.ok("User profile updated successfully");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserProfile>> getUserProfilesByUserId(@PathVariable Long userId,
                                                                     HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userType = (Integer) session.getAttribute("userType");

        if (userType == null || userType != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<UserProfile> userProfiles = userProfileService.getUserProfilesByUserId(userId);
        return ResponseEntity.ok(userProfiles);
    }
}
