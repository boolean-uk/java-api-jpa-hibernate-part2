package com.booleanuk.api.controller;

import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.BookRepository;
import com.booleanuk.api.repository.PublisherRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    public List<Publisher> getAllPublishers(){
        return this.publisherRepository.findAll();
    }
    @GetMapping("{id}")
    public Publisher getPublisherById(@PathVariable int id){
        return this.publisherRepository.findById(id).orElseThrow();

    }
    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher){
        return new ResponseEntity<Publisher>(this.publisherRepository.save(publisher), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher){
        Publisher publisherToUpdate = this.publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Publisher not found!!!"));

        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());


        return new ResponseEntity<Publisher>(this.publisherRepository.save(publisherToUpdate),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id){
        Publisher publisherToDelete = this.publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Publisher not found!!!"));
        this.publisherRepository.delete(publisherToDelete);
        return  ResponseEntity.ok(publisherToDelete);
    }
}
