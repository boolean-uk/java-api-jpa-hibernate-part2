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
    public List<Publisher> getAll() {
        return this.publisherRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        if(containsNull(publisher)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create publisher, please check all required fields are correct.");
        }
        return new ResponseEntity<>(publisherRepository.save(publisher), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable int id) {
        Publisher publisher = findPublisher(id);
        return ResponseEntity.ok(publisher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToDelete = findPublisher(id);
        if(!publisherToDelete.getBooks().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete the publisher because it has books attached to it.");
        }
        publisherRepository.delete(publisherToDelete);
        return ResponseEntity.ok(publisherToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = findPublisher(id);
        if(containsNull(publisher)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the publisher, please check all required fields are correct.");
        }
        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());

        return new ResponseEntity<>(publisherRepository.save(publisherToUpdate), HttpStatus.CREATED);
    }

    private Publisher findPublisher(int id) {
        return publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers with that id were found"));
    }

    private boolean containsNull(Publisher publisher) {
        return publisher.getName() == null || publisher.getLocation() == null;
    }
}

