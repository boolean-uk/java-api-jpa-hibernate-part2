package com.booleanuk.api.controller;

import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("publishers")
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepository;

    public PublisherController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    // Get all publishers
    @GetMapping
    public List<Publisher> getAllPublishers() {
        return this.publisherRepository.findAll();
    }

    // Get one publisher by ID
    @GetMapping("/{id}")
    public Publisher getPublisherById(@PathVariable int id) {
        return this.publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found"));
    }

    // Create a new publisher
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Publisher createPublisher(@RequestBody Publisher publisher) {
        return this.publisherRepository.save(publisher);
    }

    // Update an existing publisher by ID
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Publisher updatePublisher(@PathVariable int id, @RequestBody Publisher updatedPublisher) {
        return this.publisherRepository.findById(id)
                .map(publisher -> {
                    publisher.setName(updatedPublisher.getName());
                    publisher.setLocation(updatedPublisher.getLocation());
                    return this.publisherRepository.save(publisher);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found"));
    }

    // Delete a publisher by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePublisher(@PathVariable int id) {
        Publisher publisher = this.publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found"));
        this.publisherRepository.delete(publisher);
    }
}

