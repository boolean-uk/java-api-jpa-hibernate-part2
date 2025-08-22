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


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Publisher publisher) {
        try {
            Publisher savedPublisher = this.publisherRepository.save(publisher);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPublisher);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Could not create publisher, please check all required fields are correct.");
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Publisher> getAll() {
        return this.publisherRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        if (this.publisherRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(this.publisherRepository.findById(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No publishers with that id were found");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public ResponseEntity<?> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher existingpublisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers with that id found"));
        if (publisher.getLocation() == null || publisher.getName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not update the publisher, please check all required fields are correct.");
        }

        existingpublisher.setName(publisher.getName());
        existingpublisher.setLocation(publisher.getLocation());
        Publisher updatedPublisher = publisherRepository.save(existingpublisher);
        return new ResponseEntity<>(updatedPublisher, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable int id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers with that id found"));

        this.publisherRepository.delete(publisher);
        return ResponseEntity.ok(publisher);

    }

}
