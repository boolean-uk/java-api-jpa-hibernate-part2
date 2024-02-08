package com.booleanuk.api.controller;

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
    public ResponseEntity<Publisher> getById(@PathVariable int id) {
        Publisher publisher = this.getPublisher(id);
        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        this.checkHasRequiredValues(publisher);
        Publisher createdPublisher = this.publisherRepository.save(publisher);
        createdPublisher.setBooks(new ArrayList<Book>());
        return new ResponseEntity<>(createdPublisher, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToDelete = this.getPublisher(id);
        this.publisherRepository.delete(publisherToDelete);
        publisherToDelete.setBooks(new ArrayList<Book>());
        return ResponseEntity.ok(publisherToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        this.checkHasRequiredValues(publisher);
        Publisher publisherToUpdate = this.getPublisher(id);
        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());
        publisherToUpdate.setBooks(new ArrayList<Book>());
        return new ResponseEntity<>(this.publisherRepository.save(publisherToUpdate), HttpStatus.CREATED);
    }

    private Publisher getPublisher(int id) {
        return this.publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers with that id were found"));
    }

    private void checkHasRequiredValues(Publisher publisher) {
        if (publisher.getLocation() == null || publisher.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please check all required fields are correct.");
        }
    }
}
