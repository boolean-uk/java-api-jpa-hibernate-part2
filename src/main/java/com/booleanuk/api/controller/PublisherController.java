package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("publishers")
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepository;

//    public PublisherController(PublisherRepository publisherRepository) {
//        this.publisherRepository = publisherRepository;
//    }

    @GetMapping
    public List<Publisher> getAll() {
        return this.publisherRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getById(@PathVariable int id) {
        Publisher publisher = this.publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        return ResponseEntity.ok(publisher);
    }
//    record PostPublisher(String name, String location) {}

    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher publisher) {
        return ResponseEntity.ok(this.publisherRepository.save(publisher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = this.publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());

        publisherToUpdate.setBooks(new ArrayList<>());


        return new ResponseEntity<Publisher>(publisherRepository.save(publisherToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToDelete = this.publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.publisherRepository.delete(publisherToDelete);

        return ResponseEntity.ok(publisherToDelete);
    }

}
