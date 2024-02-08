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
    private PublisherRepository publisherRepository;

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return this.publisherRepository.findAll();
    }

    @GetMapping("/{id}")
    public Publisher getPublisherId(@PathVariable int id) {
        return this.publisherRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found"));
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        if(publisher.getName() == null || publisher.getLocation() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        this.publisherRepository.save(publisher);
        publisher.setBooks(new ArrayList<>());
        return new ResponseEntity<>(publisher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        if(publisher.getName() == null || publisher.getLocation() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        Publisher publisher1 = this.getPublisherId(id);
        publisher1.setName(publisher.getName());
        publisher1.setLocation(publisher.getLocation());
        return new ResponseEntity<>(this.publisherRepository.save(publisher1), HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public Publisher deletePublisher(@PathVariable int id) {
       Publisher publisher = this.getPublisherId(id);
       try {
           this.publisherRepository.delete(publisher);
       } catch (Exception e){
           throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Publisher still refernces a book");
       }
       publisher.setBooks(new ArrayList<>());
       return publisher;
    }
}