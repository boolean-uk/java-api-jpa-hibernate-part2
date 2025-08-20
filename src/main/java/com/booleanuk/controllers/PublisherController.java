package com.booleanuk.controllers;

import com.booleanuk.models.Publisher;
import com.booleanuk.repositories.PublisherRepository;
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
    private PublisherRepository publisherRepository;

    @GetMapping
    public ResponseEntity<List<Publisher>> getAll() {
        return ResponseEntity.ok(this.publisherRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher publisher) {
        return new ResponseEntity<Publisher>(this.publisherRepository.save(publisher), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getOne(@PathVariable int id) {
        Publisher publisher = null;
        publisher = this.publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(publisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> update(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher updated = this.publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        updated.setName(publisher.getName());
        updated.setLocation(publisher.getLocation());

        return new ResponseEntity<Publisher>(this.publisherRepository.save(updated), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> delete(@PathVariable int id) {
        Publisher toDelete = this.publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        this.publisherRepository.delete(toDelete);

        return ResponseEntity.ok(toDelete);
    }
}
