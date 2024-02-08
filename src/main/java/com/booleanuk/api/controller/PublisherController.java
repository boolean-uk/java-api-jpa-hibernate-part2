package com.booleanuk.api.controller;


import com.booleanuk.api.model.Publisher;

import com.booleanuk.api.repository.PublisherRepository;
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
    private PublisherRepository repository;

    @GetMapping
    public List<Publisher> getALlPublishers() {
        return this.repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        return new ResponseEntity<>(this.repository.save(publisher), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable int id) {
        return ResponseEntity.ok(this.getAPublisher(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher author = this.getAPublisher(id);
        this.repository.delete(author);
        return ResponseEntity.ok(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher publisherToBeUpdated = this.getAPublisher(id);
        publisherToBeUpdated.setName(publisher.getName());
        publisherToBeUpdated.setLocation(publisher.getLocation());

        return new ResponseEntity<>(this.repository.save(publisherToBeUpdated), HttpStatus.CREATED);
    }

    private Publisher getAPublisher(int id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
    }





}
