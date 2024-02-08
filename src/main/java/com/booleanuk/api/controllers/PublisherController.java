package com.booleanuk.api.controllers;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repositories.PublisherRepository;
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
    PublisherRepository repository;

    @GetMapping
    public List<Publisher> getAll() {
        return this.repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher publisher) {
        if (publisher.getName() == null | publisher.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        this.repository.save(publisher);
        publisher.setBooks(new ArrayList<>());
        return new ResponseEntity<>(publisher, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Publisher getOne(@PathVariable int id) {
        return this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> update(@PathVariable int id, @RequestBody Publisher publisherReq) {
        if (publisherReq.getName() == null | publisherReq.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        Publisher publisher = this.getOne(id);
        publisher.setName(publisherReq.getName());
        publisher.setLocation(publisherReq.getLocation());

        return new ResponseEntity<>(this.repository.save(publisher), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public Publisher delete(@PathVariable int id) {
        Publisher publisher = this.getOne(id);
        try {
            this.repository.delete(publisher);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Publisher still references a book");
        }
        publisher.setBooks(new ArrayList<>());
        return publisher;
    }
}
