package com.amazon.demo.repository;

import com.amazon.demo.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    // Additional methods can be added if needed
}
