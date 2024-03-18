package com.amazon.demo.service;

import com.amazon.demo.model.Subscription;
import com.amazon.demo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    List<Subscription> getUserSubscriptions(String username);
    Optional<User> findUserWithSubscriptionsByUsername(String username);
}
