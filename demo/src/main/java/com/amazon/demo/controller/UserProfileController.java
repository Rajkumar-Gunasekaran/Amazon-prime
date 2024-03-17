package com.amazon.demo.controller;

import com.amazon.demo.model.UserProfile;
import com.amazon.demo.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    public ResponseEntity<String> createUserProfile(@RequestBody UserProfile userProfile) {
        userProfileService.createUserProfile(userProfile);
        return ResponseEntity.ok("User profile created successfully");
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<String> updateUserProfile(@PathVariable Long profileId, @RequestBody UserProfile updatedProfile) {
        userProfileService.updateUserProfile(profileId, updatedProfile);
        return ResponseEntity.ok("User profile updated successfully");
    }
}
