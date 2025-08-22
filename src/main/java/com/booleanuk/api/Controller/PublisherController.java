package com.booleanuk.api.Controller;

import com.booleanuk.api.Model.Publisher;
import com.booleanuk.api.Repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("publishers")
public class PublisherController {
    @Autowired
    private PublisherRepository publisherRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Publisher newPublisher) {
        try {
            Publisher savedPublisher = this.publisherRepository.save(newPublisher);
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
        Optional<Publisher> publisher = this.publisherRepository.findById(id);
        if (publisher.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(publisher.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No publishers with that id were found.");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody Publisher updatedPublisher) {
        Optional<Publisher> existingPublisherOptional = this.publisherRepository.findById(id);

        if (existingPublisherOptional.isPresent()) {
            try {
                Publisher existingPublisher = existingPublisherOptional.get();
                existingPublisher.setName(updatedPublisher.getName());
                existingPublisher.setLocation(updatedPublisher.getLocation());
                Publisher savedPublisher = this.publisherRepository.save(existingPublisher);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedPublisher);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Could not update publisher, please check all required fields are correct.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No publishers with that id were found.");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public Publisher delete(@PathVariable("id") Integer id) {
        Publisher returnPublisher = this.publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No publishers with that ID were found")
        );
        this.publisherRepository.deleteById(id);
        return returnPublisher;
    }
}