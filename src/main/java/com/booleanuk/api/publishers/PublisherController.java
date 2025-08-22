package com.booleanuk.api.publishers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    private PublisherRepository publisherRepository;

    public PublisherController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> getAll() {
        return ResponseEntity.ok(publisherRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getOne(@PathVariable(name="id") int id) {
        return ResponseEntity.ok(publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that ID was found.")
        ));
    }

    @PostMapping
    public ResponseEntity<Publisher> createOne(@RequestBody Publisher publisher) {
        return new ResponseEntity<>(publisherRepository.save(publisher), HttpStatus.CREATED);
        //Could not create publisher. Please check all required fields are correct.
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> update(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that ID was found.")
        );
        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());
        Publisher updatedPublisher = publisherRepository.save(publisherToUpdate);
        return new ResponseEntity<>(updatedPublisher, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> delete(@PathVariable int id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that ID was found.")
        );
        publisherRepository.deleteById(id);
        return ResponseEntity.ok(publisher);
    }
}
