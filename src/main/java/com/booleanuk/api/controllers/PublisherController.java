package com.booleanuk.api.controllers;


import com.booleanuk.api.model.Publisher;
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
    PublisherRepository publisherRepository;

    @GetMapping
    public ResponseEntity<List<Publisher>> getAll() {
        return ResponseEntity.ok(this.publisherRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getOne(@PathVariable int id) {
        Publisher publisher = findById(id);

        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher publisher) {
        if(publisher.getName() == null || publisher.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create publisher please check all required fields are correct.");
        }
        return new ResponseEntity<>(this.publisherRepository.save(publisher), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> update(@RequestBody Publisher publisher, @PathVariable int id) {
        Publisher publisherToUpdate = findById(id);
        if(publisher.getName() == null || publisher.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update publisher please check all required fields are correct.");
        }
        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());

        return new ResponseEntity<>(this.publisherRepository.save(publisherToUpdate), HttpStatus.CREATED);
    }

    private Publisher findById(int id) {
        return this.publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors with that id were found")
        );

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> delete(@PathVariable int id) {
        Publisher publisher = findById(id);
        this.publisherRepository.delete(publisher);
        return ResponseEntity.ok(publisher);

    }


}
