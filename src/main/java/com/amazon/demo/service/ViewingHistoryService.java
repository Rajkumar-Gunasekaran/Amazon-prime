package com.amazon.demo.service;

import com.amazon.demo.model.ViewingHistory;
import com.amazon.demo.repository.ViewingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewingHistoryService {

    private final ViewingHistoryRepository viewingHistoryRepository;

    @Autowired
    public ViewingHistoryService(ViewingHistoryRepository viewingHistoryRepository) {
        this.viewingHistoryRepository = viewingHistoryRepository;
    }

    public List<ViewingHistory> getViewingHistoryByUserId(Long userId) {
        return viewingHistoryRepository.findByUserId(userId);
    }
}
