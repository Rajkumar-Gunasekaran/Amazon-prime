package com.amazon.demo.service;

import com.amazon.demo.model.Content;
import com.amazon.demo.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentService {

    private final ContentRepository contentRepository;

    @Autowired
    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public List<Content> getMovieTitlesByCategory(String category) {
        return contentRepository.findByCategory(category);
    }

    public Optional<Content> searchByTitle(String title) {
        return contentRepository.findByTitle(title);
    }
}
