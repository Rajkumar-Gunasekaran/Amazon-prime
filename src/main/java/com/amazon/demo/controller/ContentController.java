package com.amazon.demo.controller;

import com.amazon.demo.model.Content;
import com.amazon.demo.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<List<Content>> getMovieTitlesByCategory(@PathVariable String category,
                                                                  HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userType = (Integer) session.getAttribute("userType");

        if (userType == null || userType != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Content> movieTitles = contentService.getMovieTitlesByCategory(category);
        return ResponseEntity.ok(movieTitles);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByTitle(@RequestParam("title") String title,
                                           HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userType = (Integer) session.getAttribute("userType");

        if (userType == null || userType != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

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
                                             @RequestParam("location") String location,
                                             HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userType = (Integer) session.getAttribute("userType");

        if (userType == null || userType != 0) { // Allow only admin (userType 0) to add content
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Content content = new Content();
        content.setTitle(title);
        content.setDescription(description);
        content.setCategory(category);
        content.setLocation(location);

        contentService.saveContent(content);

        return ResponseEntity.ok("Content added successfully");
    }

}
