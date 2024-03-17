package com.amazon.demo.repository;

import com.amazon.demo.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Integer> {
    List<Content> findByCategory(String category);

    Optional<Content> findByTitle(String title);
}
