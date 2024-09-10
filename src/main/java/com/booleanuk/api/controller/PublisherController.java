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

    public PublisherController(PublisherRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Publisher> getAll() {
        return this.repository.findAll();
    }

    @GetMapping("{id}")
    public Publisher getById(@PathVariable("id") Integer id) {
        return this.repository.findById(id).orElseThrow();
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        return new ResponseEntity<>(this.repository.save(publisher), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id,
                                           @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existing publisher with that ID found")
        );
        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());
        return new ResponseEntity<>(this.repository.save(publisherToUpdate),
                HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToDelete = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No publishers with that ID were found")
        );
        this.repository.delete(publisherToDelete);
        return ResponseEntity.ok(publisherToDelete);
    }
}

