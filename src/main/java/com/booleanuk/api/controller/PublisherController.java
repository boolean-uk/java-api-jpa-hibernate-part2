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
    PublisherRepository publisherRepository;

    @PostMapping
    public ResponseEntity<Publisher> addPublisher(@RequestBody Publisher publisher){
        return new ResponseEntity<>(this.publisherRepository.save(publisher), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers(){
        return new ResponseEntity<>(this.publisherRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable int id){
        Publisher publisherToFind = this.publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find Publisher with the provided id"));
        return new ResponseEntity<>(publisherToFind, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher){
        Publisher publisherToUpdate = this.publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the Publisher to update"));
        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());
        publisherToUpdate.setBooks(publisher.getBooks());

        return new ResponseEntity<>(this.publisherRepository.save(publisherToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id){
        Publisher publisherToDelete = this.publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the Publisher to delete"));
        this.publisherRepository.delete(publisherToDelete);
        return new ResponseEntity<Publisher>(publisherToDelete, HttpStatus.OK);
    }
}
