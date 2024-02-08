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
@RequestMapping("publishers")
public class PublisherController {
    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return this.publisherRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getAPublisher(@PathVariable int id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        Publisher newPublisher = this.publisherRepository.save(publisher);
        newPublisher.setBooks(new ArrayList<>());
        return new ResponseEntity<>(newPublisher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        String regex = "/^[a-zA-Z\\s]*$/";
        if(publisher.getName().matches(regex) && publisher.getLocation().matches(regex)) {
            publisherToUpdate.setName(publisher.getName());
            publisherToUpdate.setLocation(publisher.getLocation());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parameters conflicts with requirements");
        }
        return new ResponseEntity<>(this.publisherRepository.save(publisherToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToDelete = publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        if(!publisherToDelete.getBooks().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Publisher has books, and could not be deleted");
        }
        this.publisherRepository.delete(publisherToDelete);
        publisherToDelete.setBooks(new ArrayList<>());
        return ResponseEntity.ok(publisherToDelete);
    }
}
