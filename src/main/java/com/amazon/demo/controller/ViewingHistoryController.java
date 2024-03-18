package com.amazon.demo.controller;

import com.amazon.demo.model.ViewingHistory;
import com.amazon.demo.service.ViewingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<ViewingHistory>> getViewingHistoryByUserId(@PathVariable Long userId) {
        List<ViewingHistory> viewingHistory = viewingHistoryService.getViewingHistoryByUserId(userId);
        return ResponseEntity.ok(viewingHistory);
    }
}
