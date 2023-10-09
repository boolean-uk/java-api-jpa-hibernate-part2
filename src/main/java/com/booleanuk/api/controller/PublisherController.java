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
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public List<Publisher> getAll() {
        return this.publisherRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getById(@PathVariable int id) {
        Publisher publisher = null;
        publisher = this.publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry this publisher doesn't exist"));
        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher publisher) {
        return new ResponseEntity<>(this.publisherRepository.save(publisher), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatedPublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = this.getById(id).getBody();
        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());
        return new ResponseEntity<>(this.publisherRepository.save(publisherToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> publisherToDelete(@PathVariable int id) {
        Publisher publisherToDelete = this.publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry can't delete publisher, with this id"));
        this.publisherRepository.delete((publisherToDelete));
        return ResponseEntity.ok(publisherToDelete);
    }
}
