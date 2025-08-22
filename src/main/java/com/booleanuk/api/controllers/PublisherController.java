package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.repositories.PublisherRepository;
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

    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher publisher) {
        return new ResponseEntity<>(this.repository.save(publisher), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getById(@PathVariable int id) {
        Publisher publisher = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No publisher with id "+id+" found.")
        );
        return ResponseEntity.ok(publisher);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> update(@PathVariable int id, @RequestBody Publisher publisher) {
        // Check if publisher id exist
        this.getById(id);

        publisher.setId(id);
        return new ResponseEntity<>(this.repository.save(publisher), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> delete(@PathVariable int id) {
        // Check if publisher id exist
        ResponseEntity<Publisher> publisher = this.getById(id);

        try {
            this.repository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Coudn't delete publisher. Details: "+e.getMessage());
        }
        return publisher;
    }
}
