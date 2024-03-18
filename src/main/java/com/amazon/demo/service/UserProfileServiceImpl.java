package com.amazon.demo.service;

import com.amazon.demo.model.User;
import com.amazon.demo.model.UserProfile;
import com.amazon.demo.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public void createUserProfile(UserProfile userProfile) {
        userProfile.setCreatedAt(LocalDateTime.now());
        userProfile.setUpdatedAt(LocalDateTime.now());
        userProfileRepository.save(userProfile);
    }

    @Override
    public void updateUserProfile(Long profileId, UserProfile updatedProfile) {
        Optional<UserProfile> existingProfile = userProfileRepository.findById(profileId);

        existingProfile.ifPresent(profile -> {
            profile.setFirstName(updatedProfile.getFirstName());
            profile.setLastName(updatedProfile.getLastName());
            profile.setProfilePicture(updatedProfile.getProfilePicture());
            profile.setPreferences(updatedProfile.getPreferences());
            profile.setUpdatedAt(LocalDateTime.now());

            userProfileRepository.save(profile);
        });
    }
    @Override
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.subscriptions WHERE u.username = :username")
    public Optional<User> findUserWithSubscriptionsByUsername(@Param("username") String username) {
        // This query fetches the user and eagerly fetches subscriptions
        UserProfileServiceImpl userRepository = null;
        Optional<User> userOptional = userRepository.findUserWithSubscriptionsByUsername(username);
        System.out.println("User with Subscriptions: " + userOptional.orElse(null));
        return userOptional;
    }

}
