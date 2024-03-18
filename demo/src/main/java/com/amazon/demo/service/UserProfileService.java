package com.amazon.demo.service;

import com.amazon.demo.model.User;
import com.amazon.demo.model.UserProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserProfileService {
    void createUserProfile(UserProfile userProfile);
    void updateUserProfile(Long profileId, UserProfile updatedProfile);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.subscriptions WHERE u.username = :username")
    Optional<User> findUserWithSubscriptionsByUsername(@Param("username") String username);
}
