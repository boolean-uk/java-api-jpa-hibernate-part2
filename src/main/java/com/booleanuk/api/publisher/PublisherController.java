package com.booleanuk.api.publisher;

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

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        if (publisher == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.publisherRepository.save(publisher), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return this.publisherRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisher(@PathVariable int id) {
        return new ResponseEntity<>(this.publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No publisher with that id were found")), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        if (publisher == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the publisher, please check all required fields are correct");
        }
        Publisher updatePublisher = this.publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No publisher with that id were found "));
        updatePublisher.setName(publisher.getName());
        updatePublisher.setLocation(publisher.getLocation());


        return new ResponseEntity<>(this.publisherRepository.save(updatePublisher), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher deletedPublisher = this.publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id were found"));
        this.publisherRepository.delete(deletedPublisher);
        return ResponseEntity.ok(deletedPublisher);
    }
}
