package com.booleanuk.api.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    // Create a new publisher
    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        Publisher createdPublisher = publisherRepository.save(publisher);
        return new ResponseEntity<>(createdPublisher, HttpStatus.CREATED);
    }

    // Get all publishers
    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        List<Publisher> publishers = publisherRepository.findAll();
        return new ResponseEntity<>(publishers, HttpStatus.OK);
    }

    // Get a specific publisher by ID
    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable("id") int id) {
        Optional<Publisher> publisherOptional = publisherRepository.findById(id);
        return publisherOptional.map(publisher -> new ResponseEntity<>(publisher, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an existing publisher
    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable("id") int id, @RequestBody Publisher updatedPublisher) {
        Optional<Publisher> publisherOptional = publisherRepository.findById(id);
        if (publisherOptional.isPresent()) {
            updatedPublisher.setId(id); // Ensure the ID matches the path variable
            Publisher savedPublisher = publisherRepository.save(updatedPublisher);
            return new ResponseEntity<>(savedPublisher, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a publisher by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePublisher(@PathVariable("id") int id) {
        Optional<Publisher> publisherOptional = publisherRepository.findById(id);
        if (publisherOptional.isPresent()) {
            publisherRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
