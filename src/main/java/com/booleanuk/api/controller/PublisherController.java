package com.booleanuk.api.controller;

import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    @Autowired
    private PublisherRepository publishers;

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        Publisher publisherToCreate;
        try {
            publisherToCreate = this.publishers.save(publisher);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Create failed. Check required fields.");
        }
        return new ResponseEntity<>(publisherToCreate, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return this.publishers.findAll(Sort.by(Sort.Direction.ASC,"publisherId"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisher(@PathVariable int id) {
        Publisher publisherToGet = this.publishers.findById(id).orElse(null);
        if (publisherToGet == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher with this id was not found.");
        }
        return ResponseEntity.ok(publisherToGet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = this.publishers.findById(id).orElse(null);
        if (publisherToUpdate == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher with this id was not found.");
        }
        try {
            publisherToUpdate.setName(publisher.getName());
            publisherToUpdate.setLocation(publisher.getLocation());
            publisherToUpdate = this.publishers.save(publisherToUpdate);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Update failed. Check required fields.");
        }
        return new ResponseEntity<>(publisherToUpdate, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToDelete = this.publishers.findById(id).orElse(null);
        if (publisherToDelete == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher with this id was not found.");
        }
        this.publishers.delete(publisherToDelete);
        return ResponseEntity.ok(publisherToDelete);
    }
}
