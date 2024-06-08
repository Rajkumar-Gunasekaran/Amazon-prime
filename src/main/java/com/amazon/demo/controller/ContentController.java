package com.amazon.demo.controller;

import com.amazon.demo.model.Content;
import com.amazon.demo.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public ResponseEntity<?> searchByTitle(@RequestParam("title") String title) {
        Optional<Content> contentOptional = contentService.searchByTitle(title);

        if (contentOptional.isPresent()) {
            Content content = contentOptional.get();
            String location = content.getLocation();
            String actors = content.getActors();
            return ResponseEntity.ok(content);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/add")
    public ResponseEntity<String> addContent(@RequestParam("title") String title,
                                             @RequestParam("description") String description,
                                             @RequestParam("category") String category,
                                             @RequestParam("location") String location) {
        Content content = new Content();
        content.setTitle(title);
        content.setDescription(description);
        content.setCategory(category);
        content.setLocation(location);

        contentService.saveContent(content);

        return ResponseEntity.ok("Content added successfully");
    }
}
