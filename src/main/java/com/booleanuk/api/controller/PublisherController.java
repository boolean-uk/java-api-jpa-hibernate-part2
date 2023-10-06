package com.booleanuk.api.controller;

import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("publishers")
public class PublisherController {

    @Autowired
    private PublisherRepo publisherRepo;

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return this.publisherRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublisherById(@PathVariable int id) {
        try {
            Publisher publisher = this.publisherRepo.findById(id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
            return ResponseEntity.ok(publisher);
        } catch (Exception e) {
            return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createPublisher(@RequestBody Publisher publisher) {
        try {
            return new ResponseEntity<>(this.publisherRepo.save(publisher), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        try {
           Publisher updatedPublisher = this.publisherRepo.findById(id).orElseThrow(() ->
                   new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
           updatedPublisher.setName(publisher.getName());
           updatedPublisher.setLocation(publisher.getLocation());
           return new ResponseEntity<>(this.publisherRepo.save(updatedPublisher), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable int id) {
        try {
            Publisher deletedPublisher = this.publisherRepo.findById(id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
            this.publisherRepo.delete(deletedPublisher);
            return ResponseEntity.ok(deletedPublisher);
        } catch (Exception e) {
            return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
        }
    }


}