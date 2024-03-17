// ContentController.java
package com.amazon.demo.controller;

import com.amazon.demo.model.Content;
import com.amazon.demo.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/content")
public class ContentController {

    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/titles/{category}")
    public ResponseEntity<List<Content>> getMovieTitlesByCategory(@PathVariable String category) {
        List<Content> movieTitles = contentService.getMovieTitlesByCategory(category);
        return ResponseEntity.ok(movieTitles);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<?> searchByTitle(@PathVariable String title) {
        Optional<Content> contentOptional = contentService.searchByTitle(title);

        if (contentOptional.isPresent()) {
            Content content = contentOptional.get();
            return ResponseEntity.ok(content);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
