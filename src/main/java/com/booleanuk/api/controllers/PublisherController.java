package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.repositories.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("publishers")
public class PublisherController {
    record PostPublisher(String name, String location) {}
    
    @Autowired
    private PublisherRepo publisherRepository;

    @GetMapping
    public List<Publisher> getAll() {
        return publisherRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getById(@PathVariable final Integer id) {
        return new ResponseEntity<>(publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody PostPublisher request) {
        return new ResponseEntity<>(publisherRepository.save(new Publisher(request.name, request.location)), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> update(@PathVariable final Integer id, @RequestBody final Publisher publisher) {
        Publisher _targetPublisher = publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found."));

        _targetPublisher.setName(publisher.getName());
        _targetPublisher.setLocation(publisher.getLocation());

        return new ResponseEntity<>(publisherRepository.save(_targetPublisher), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> remove(@PathVariable final Integer id) {
        return new ResponseEntity<>(publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")), HttpStatus.OK);
    }
}
