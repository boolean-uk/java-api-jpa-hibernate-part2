package com.booleanuk.api.controllers;

import com.booleanuk.api.modules.Publisher;
import com.booleanuk.api.repositories.PublisherRepository;
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
    public List<Publisher> getAll(){
        return this.publisherRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable int id ){
        Publisher publisher = findOnePublisher(id);
        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<Publisher> addPublisher(@RequestBody Publisher publisher){
        return new ResponseEntity<Publisher>(this.publisherRepository.save(publisher),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher){
        Publisher updatePublisher = findOnePublisher(id);
        updatePublisher.setName(publisher.getName());
        updatePublisher.setLocation(publisher.getLocation());
        return new ResponseEntity<Publisher>(this.publisherRepository.save(updatePublisher),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id){
        Publisher deletePublisher = findOnePublisher(id);
        this.publisherRepository.delete(deletePublisher);
        return ResponseEntity.ok(deletePublisher);
    }

    private Publisher findOnePublisher(int id){
        return this.publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
