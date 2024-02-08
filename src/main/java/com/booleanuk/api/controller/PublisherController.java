package com.booleanuk.api.controller;

import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable("id") int id) {
        Publisher publisher = publisherRepository
                .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        checkIfValidPublisher(publisher);
        Publisher newPublisher = publisherRepository.save(publisher);
        return new ResponseEntity<>(newPublisher, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public Publisher updatePublisher(@PathVariable("id") int id, @RequestBody Publisher publisher) {
        checkIfValidPublisher(publisher);
        Publisher publisherToUpdate = publisherRepository
                .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        if (publisherToUpdate != null) {
            publisherToUpdate.setName(publisher.getName());
            publisherToUpdate.setLocation(publisher.getLocation());
            publisherRepository.save(publisherToUpdate);
        }
        return publisherToUpdate;
    }

    @DeleteMapping("{id}")
    public Publisher deletePublisher(@PathVariable("id") int id) {
        Publisher publisherToDelete = publisherRepository
                .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        if (publisherToDelete != null) {
            publisherRepository.delete(publisherToDelete);
        }
        return publisherToDelete;
    }

    public void checkIfValidPublisher(Publisher publisher) {
        if (publisher.getName() == null || publisher.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }
        if (publisher.getLocation() == null || publisher.getLocation().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location is required");
        }
    }
}
