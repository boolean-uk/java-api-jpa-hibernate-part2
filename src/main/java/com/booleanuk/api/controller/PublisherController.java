package com.booleanuk.api.controller;


import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
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
    private final PublisherRepository repository;

    @Autowired
    public PublisherController (PublisherRepository repository){this.repository = repository;}

    @GetMapping
    public List<Publisher> getAllPublishers(){
        return this.repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher){
        return new ResponseEntity<Publisher>(this.repository.save(publisher), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable int id){
        Publisher publisher = null;
        publisher = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that ID found")
        );
        return ResponseEntity.ok(publisher);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher){
        Publisher publisherToUpdate = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No employees with that ID found")
        );
        publisher.setId(publisherToUpdate.getId());
        return new ResponseEntity<>(this.repository.save(publisher), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id){
        Publisher publisherToDelete = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No publisher with that ID found")
        );
        this.repository.delete(publisherToDelete);
        return ResponseEntity.ok(publisherToDelete);
    }

}