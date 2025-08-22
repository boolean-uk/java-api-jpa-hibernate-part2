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
    private PublisherRepository publishers;

    @GetMapping
    public ResponseEntity<List<Publisher>> getAll() {
        return new ResponseEntity<>(this.publishers.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher toAdd) {
        return new ResponseEntity<>(this.publishers.save(toAdd), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getOne(@PathVariable int id) {
        Publisher Publisher = this.publishers.findById(id).
                orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")
                );
        return new ResponseEntity<>(Publisher, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> delete(@PathVariable int id) {
        Publisher toDelete = this.publishers.findById(id).
                orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")
                );
        this.publishers.delete(toDelete);
        return new ResponseEntity<>(toDelete, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher newData) {
        Publisher publisherToUpdate = this.publishers.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")
                );

        publisherToUpdate.setName(newData.getName());
        publisherToUpdate.setLocation(newData.getLocation());

        return new ResponseEntity<>(this.publishers.save(publisherToUpdate), HttpStatus.CREATED);
    }


}
