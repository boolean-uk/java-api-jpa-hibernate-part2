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

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return this.publisherRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisher(@PathVariable int id) {
        Publisher publisher = this.publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "publisher not found"));
        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        if (publisher.getName() == null || publisher.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
        }
        return new ResponseEntity<>(this.publisherRepository.save(publisher), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        if (publisher.getName() == null || publisher.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
        } else {
            Publisher publisherToUpdate = this.publisherRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found to update"));

            publisherToUpdate.setName(publisher.getName());
            publisherToUpdate.setLocation(publisher.getLocation());

            return new ResponseEntity<>(this.publisherRepository.save(publisherToUpdate), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToDelete = this.publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found to delete"));
        this.publisherRepository.delete(publisherToDelete);

        return ResponseEntity.ok(publisherToDelete);
    }
}
