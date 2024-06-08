package com.amazon.demo.service;

import com.amazon.demo.model.Content;
import com.amazon.demo.model.Watchlist;
import com.amazon.demo.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final ContentService contentService;

    @Autowired
    public WatchlistService(WatchlistRepository watchlistRepository, ContentService contentService) {
        this.watchlistRepository = watchlistRepository;
        this.contentService = contentService;
    }

    public void addToWatchlist(Watchlist watchlistItem) {
        watchlistRepository.save(watchlistItem);
    }

    public List<Watchlist> getWatchlistByUserId(Long userId) {
        List<Watchlist> watchlist = watchlistRepository.findByUserId(userId);
        List<Watchlist> watchlistWithContentDetails = new ArrayList<>();

        for (Watchlist item : watchlist) {
            Long contentId = item.getContentId();
            Optional<Content> contentOptional = contentService.getContentById(contentId);

            contentOptional.ifPresent(content -> {
                item.setContent(content);
                watchlistWithContentDetails.add(item);
            });
        }

        return watchlistWithContentDetails;
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
