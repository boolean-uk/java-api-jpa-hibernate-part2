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
    private PublisherRepository publisherRepository;

    @GetMapping
    public List<Publisher> getPublishers() {
        return publisherRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisher(@PathVariable int id) {
        Publisher publisher = publisherRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers with that id were found"));
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        if(publisher.getName() == null || publisher.getLocation() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not create publisher, please check all required fields are correct");
        }
        return ResponseEntity.ok(publisherRepository.save(publisher));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher existingPublisher = publisherRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers with that id were found"));
        existingPublisher.setName(publisher.getName());
        existingPublisher.setLocation(publisher.getLocation());
        if(existingPublisher.getName() == null || existingPublisher.getLocation() == null)
        {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not update publisher, please check all required fields are correct");
        }
        return ResponseEntity.ok(publisherRepository.save(existingPublisher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisher = publisherRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id were found"));
        publisherRepository.delete(publisher);
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }
}
