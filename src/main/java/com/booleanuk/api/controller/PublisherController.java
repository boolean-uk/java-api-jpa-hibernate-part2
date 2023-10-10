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

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private final PublisherRepository publisherRepository;

    public PublisherController(PublisherRepository repository) {
        this.publisherRepository = repository;
    }

    @GetMapping
    public List<Publisher> getAll() {
        return this.publisherRepository.findAll();
    }

    @GetMapping("/{id}")
    public Publisher getPublisherById(@PathVariable int id) {
        Publisher publisher = null;
        publisher = this.publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher was not found")
        );
        return publisher;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Publisher publisherCreated(@RequestBody Publisher newPublisher) throws SQLException {
        return this.publisherRepository.save(newPublisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = this.publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher was not found")
        );

        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());




        return new ResponseEntity<>(this.publisherRepository.save(publisherToUpdate), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisherById(@PathVariable int id) {
        Publisher publisherToDelete = this.publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher was not found")
        );

        publisherRepository.delete(publisherToDelete);
        //ResponseEntity.ok stuurt een status code 200 terug, met publisherToDelete als value
        //In echte projecten 'return ResponseEntity.noContent().build();' gebruiken ipv .ok
        //Dit stuurt een code 204 terug , 204 = no content
        return ResponseEntity.ok(publisherToDelete);
    }




}
