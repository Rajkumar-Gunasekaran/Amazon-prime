package com.amazon.demo.controller;

import com.amazon.demo.model.Watchlist;
import com.amazon.demo.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

    private final WatchlistService watchlistService;

    @Autowired
    public WatchlistController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    @PostMapping
    public ResponseEntity<String> addToWatchlist(@RequestParam Long userId,
                                                 @RequestParam Long contentId,
                                                 HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userType = (Integer) session.getAttribute("userType");

        if (userType == null || userType != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Watchlist watchlistItem = new Watchlist(userId, contentId, LocalDateTime.now());
        watchlistService.addToWatchlist(watchlistItem);
        return ResponseEntity.ok("Added to watchlist successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Watchlist>> getWatchlistByUserId(@PathVariable Long userId,
                                                                HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userType = (Integer) session.getAttribute("userType");

        if (userType == null || userType != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Watchlist> watchlist = watchlistService.getWatchlistByUserId(userId);
        return ResponseEntity.ok(watchlist);
    }

    @GetMapping("/item/{watchlistId}")
    public ResponseEntity<Watchlist> getWatchlistItemById(@PathVariable Long watchlistId,
                                                          HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userType = (Integer) session.getAttribute("userType");

        if (userType == null || userType != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Watchlist> watchlistItem = watchlistService.getWatchlistItemById(watchlistId);
        return watchlistItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/item/{watchlistId}")
    public ResponseEntity<String> updateWatchlistItem(@PathVariable Long watchlistId,
                                                      @RequestParam Long userId,
                                                      @RequestParam Long contentId,
                                                      HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userType = (Integer) session.getAttribute("userType");

        if (userType == null || userType != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Watchlist> existingWatchlistItem = watchlistService.getWatchlistItemById(watchlistId);

        if (existingWatchlistItem.isPresent()) {
            Watchlist updatedWatchlistItem = new Watchlist(userId, contentId, LocalDateTime.now());
            updatedWatchlistItem.setWatchlistId(watchlistId);
            watchlistService.updateWatchlistItem(updatedWatchlistItem);
            return ResponseEntity.ok("Watchlist item updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/item/{watchlistId}")
    public ResponseEntity<String> deleteWatchlistItem(@PathVariable Long watchlistId,
                                                      HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userType = (Integer) session.getAttribute("userType");

        if (userType == null || userType != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        watchlistService.deleteWatchlistItem(watchlistId);
        return ResponseEntity.ok("Watchlist item deleted successfully");
    }
}
