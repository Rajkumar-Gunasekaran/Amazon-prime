package com.amazon.demo.controller;

import com.amazon.demo.model.Subscription;
import com.amazon.demo.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Subscription>> getUserSubscriptions(@PathVariable String username) {
        List<Subscription> subscriptions = subscriptionService.getUserSubscriptions(username);
        return ResponseEntity.ok(subscriptions);
    }

}
