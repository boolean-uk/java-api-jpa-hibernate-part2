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
@RequestMapping
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepository;

    public PublisherController(PublisherRepository publisherRepository){
        this.publisherRepository = publisherRepository;
    }

    @GetMapping
    public List<Publisher> getAll(){
        return this.publisherRepository.findAll();
    }

    @GetMapping("/{id}")
    public Publisher getById(@PathVariable int id){
        return this.publisherRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher){
        return new ResponseEntity<Publisher>(this.publisherRepository.save(publisher), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher){
        Publisher pubToUpdate = this.publisherRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        pubToUpdate.setId(publisher.getId());
        pubToUpdate.setName(publisher.getName());
        pubToUpdate.setLocation(publisher.getLocation());

        return new ResponseEntity<Publisher>(this.publisherRepository.save(pubToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id){
        Publisher pubToDelete = this.publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.publisherRepository.delete(pubToDelete);
        return  ResponseEntity.ok(pubToDelete);
    }
}
