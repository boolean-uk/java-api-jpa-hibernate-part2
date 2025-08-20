package com.booleanuk.api.controller;

import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.booleanuk.api.model.Publisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherRepository publisherRepository;

    public PublisherController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        Publisher saved = publisherRepository.save(publisher);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisher(@PathVariable Long id) {
        return publisherRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long id, @RequestBody Publisher publisher) {
        return publisherRepository.findById(id).map(existing -> {
            existing.setName(publisher.getName());
            existing.setLocation(publisher.getLocation());
            publisherRepository.save(existing);
            return ResponseEntity.status(201).body(existing);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        return publisherRepository.findById(id).map(existing -> {
            publisherRepository.delete(existing);
            return ResponseEntity.ok().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
