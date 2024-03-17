package com.amazon.demo.repository;

import com.amazon.demo.model.ViewingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViewingHistoryRepository extends JpaRepository<ViewingHistory, Integer> {
    List<ViewingHistory> findByUserId(Long userId);
}
