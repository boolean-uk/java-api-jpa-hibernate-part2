package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
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
    private PublisherRepository publisherRepository;

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return this.publisherRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable int id) {
        return ResponseEntity.ok(getAPublisher(id));
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        return new ResponseEntity<>(this.publisherRepository.save(publisher), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToDelete = getAPublisher(id);
        this.publisherRepository.delete(publisherToDelete);
        publisherToDelete.setBooks(new ArrayList<>());
        return ResponseEntity.status(HttpStatus.OK).body(publisherToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisherById(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = this.publisherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        publisherToUpdate.setName(publisher.getLocation());
        publisherToUpdate.setLocation(publisher.getLocation());
        return new ResponseEntity<>(this.publisherRepository.save(publisherToUpdate), HttpStatus.CREATED);
    }

    private Publisher getAPublisher(int id) {
        return this.publisherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found!"));
    }
}
