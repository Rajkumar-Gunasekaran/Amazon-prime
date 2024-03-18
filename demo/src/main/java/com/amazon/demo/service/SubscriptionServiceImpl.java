package com.amazon.demo.service;

import com.amazon.demo.model.Subscription;
import com.amazon.demo.model.User;
import com.amazon.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;

    @Autowired
    public SubscriptionServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Subscription> getUserSubscriptions(String username) {
        //Optional<User> userOptional = userRepository.findByUsername(username);
        //System.out.println("User from repository: " + userOptional.orElse(null));

        Optional<User> userOptional = userRepository.findUserWithSubscriptionsByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("Found user: " + user.getUsername());
            List<Subscription> subscriptions = user.getSubscriptions();
            System.out.println("User subscriptions: " + subscriptions);
            return subscriptions;
        }

        System.out.println("User not found for username: " + username);
        return Collections.emptyList();
    }

    @Override
    public Optional<User> findUserWithSubscriptionsByUsername(String username) {
        // Implement this method as needed
        // You can leave it empty for now if it's not required
        return Optional.empty();
    }
}
