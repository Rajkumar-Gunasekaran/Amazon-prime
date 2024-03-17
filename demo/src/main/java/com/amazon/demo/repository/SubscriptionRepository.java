package com.amazon.demo.repository;
import com.amazon.demo.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    // Additional methods can be added if needed
}

