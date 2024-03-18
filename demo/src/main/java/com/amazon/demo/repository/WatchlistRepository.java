package com.amazon.demo.repository;

import com.amazon.demo.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<Watchlist, Integer> {
    List<Watchlist> findByUserId(Long userId);
}
