package com.amazon.demo.controller;

import com.amazon.demo.model.Subscription;
import com.amazon.demo.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<List<Subscription>> getUserSubscriptions(@PathVariable String username,
                                                                   HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userType = (Integer) session.getAttribute("userType");

        if (userType == null || userType != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Subscription> subscriptions = subscriptionService.getUserSubscriptions(username);
        return ResponseEntity.ok(subscriptions);
    }
}
