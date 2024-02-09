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
    PublisherRepository repository;

    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher publisher) {
        return new ResponseEntity<Publisher>(this.repository.save(publisher), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Publisher> getAll() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getById(@PathVariable int id) {
        Publisher publisher = null;
        publisher = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(publisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> update(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());
        return new ResponseEntity<Publisher>(this.repository.save(publisherToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> delete(@PathVariable int id) {
        Publisher publisherToDelete = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.repository.delete(publisherToDelete);
        return ResponseEntity.ok(publisherToDelete);
    }
}
