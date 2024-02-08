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
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return this.publisherRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable int id) {
        return ResponseEntity.ok(this.getAPublisher(id));
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        if (publisher.getName() == null || publisher.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "Could not create the publisher, please check all required fields");
        }
        Publisher createdPublisher = this.publisherRepository.save(publisher);
        createdPublisher.setBook(new ArrayList<>());
        return new ResponseEntity<>(createdPublisher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        if (publisher.getName() == null || publisher.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "Could not update the publisher's details, please check all required fields");
        }

        Publisher publisherToUpdate = this.getAPublisher(id);
        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());
        publisherToUpdate.setBook(new ArrayList<>());
        return new ResponseEntity<>(this.publisherRepository.save(publisherToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToDelete = this.getAPublisher(id);
        this.publisherRepository.delete(publisherToDelete);
        publisherToDelete.setBook(new ArrayList<Book>());
        return ResponseEntity.ok(publisherToDelete);
    }

    private Publisher getAPublisher(int id) {
        return this.publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers matching that id were found"));
    }
}
