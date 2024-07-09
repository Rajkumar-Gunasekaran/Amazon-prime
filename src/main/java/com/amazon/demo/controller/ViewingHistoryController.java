package com.amazon.demo.controller;

import com.amazon.demo.model.ViewingHistory;
import com.amazon.demo.service.ViewingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/viewing-history")
public class ViewingHistoryController {

    private final ViewingHistoryService viewingHistoryService;

    @Autowired
    public ViewingHistoryController(ViewingHistoryService viewingHistoryService) {
        this.viewingHistoryService = viewingHistoryService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ViewingHistory>> getViewingHistoryByUserId(@PathVariable Long userId,
                                                                          HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userType = (Integer) session.getAttribute("userType");

        if (userType == null || userType != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<ViewingHistory> viewingHistory = viewingHistoryService.getViewingHistoryByUserId(userId);
        return ResponseEntity.ok(viewingHistory);
    }
}
