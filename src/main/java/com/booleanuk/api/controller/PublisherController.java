package com.booleanuk.api.controller;


import com.booleanuk.api.model.Author;
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
        arePublisherValid(publisher);
        Publisher createdDepartment = this.repository.save(publisher);
        createdDepartment.setBooks(new ArrayList<>());
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable int id) {
        return ResponseEntity.ok(this.getAPublisher(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToDelete = this.getAPublisher(id);
        this.repository.delete(publisherToDelete);
        publisherToDelete.setBooks(new ArrayList<>());
        return ResponseEntity.ok(publisherToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        arePublisherValid(publisher);
        Publisher publisherToBeUpdated = this.getAPublisher(id);
        publisherToBeUpdated.setName(publisher.getName());
        publisherToBeUpdated.setLocation(publisher.getLocation());

        return new ResponseEntity<>(this.repository.save(publisherToBeUpdated), HttpStatus.CREATED);
    }

    private void arePublisherValid(Publisher publisher) {
        if(publisher.getName() == null || publisher.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please check all required fields are correct.");
        }
    }

    private Publisher getAPublisher(int id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers with that id were found"));
    }





}
