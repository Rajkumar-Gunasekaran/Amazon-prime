package com.amazon.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Watchlist")
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WatchlistID")
    private Long watchlistId;

    @Column(name = "UserID")
    private Long userId;

    @Column(name = "ContentID")
    private Long contentId;

    @Column(name = "AddedAt")
    private LocalDateTime addedAt;

    // Default constructor
    public Watchlist() {
    }

    // Constructor with parameters
    public Watchlist(Long userId, Long contentId, LocalDateTime addedAt) {
        this.userId = userId;
        this.contentId = contentId;
        this.addedAt = addedAt;
    }

    // Getters and setters
    public Long getWatchlistId() {
        return watchlistId;
    }

    public void setWatchlistId(Long watchlistId) {
        this.watchlistId = watchlistId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}
