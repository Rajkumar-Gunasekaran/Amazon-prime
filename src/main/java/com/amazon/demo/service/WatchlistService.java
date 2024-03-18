package com.amazon.demo.service;

import com.amazon.demo.model.Watchlist;
import com.amazon.demo.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;

    @Autowired
    public WatchlistService(WatchlistRepository watchlistRepository) {
        this.watchlistRepository = watchlistRepository;
    }

    public void addToWatchlist(Watchlist watchlistItem) {
        watchlistRepository.save(watchlistItem);
    }

    public List<Watchlist> getWatchlistByUserId(Long userId) {
        return watchlistRepository.findByUserId(userId);
    }

    public Optional<Watchlist> getWatchlistItemById(Long watchlistId) {
        return watchlistRepository.findById(Math.toIntExact(watchlistId));
    }

    public void updateWatchlistItem(Watchlist updatedWatchlistItem) {
        watchlistRepository.save(updatedWatchlistItem);
    }

    public void deleteWatchlistItem(Long watchlistId) {
        watchlistRepository.deleteById(Math.toIntExact(watchlistId));
    }
}
