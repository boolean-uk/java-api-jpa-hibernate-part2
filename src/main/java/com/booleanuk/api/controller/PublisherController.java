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
    @Autowired
    private final PublisherRepository repository;

    public PublisherController(PublisherRepository publisherRepository) {
        this.repository = publisherRepository;
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> getAll() {
        return new ResponseEntity<>(this.repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> put(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher p = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        p.setName(publisher.getName());
        p.setLocation(publisher.getLocation());
        return new ResponseEntity<>(this.repository.save(p), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> delete(@PathVariable int id) {
        Publisher p = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        repository.delete(p);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher publisher) {
        Publisher p = new Publisher(publisher.getName(), publisher.getLocation());
        return new ResponseEntity<>(repository.save(p), HttpStatus.CREATED);
    }
}
