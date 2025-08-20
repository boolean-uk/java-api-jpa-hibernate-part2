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

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers(){
        return ResponseEntity.ok(this.publisherRepository.findAll());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher request) {
        return new ResponseEntity<Publisher>(this.publisherRepository.save(request), HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public ResponseEntity<Publisher> getById(@PathVariable("id") Integer id) {
        Publisher publisher = null;
        publisher = this.publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entered ID does not exist!")
        );

        return ResponseEntity.ok(publisher);
    }


    @PutMapping("{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = this.publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entered ID does not exist!"));

        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());

        return new ResponseEntity<Publisher>(this.publisherRepository.save(publisherToUpdate), HttpStatus.CREATED);
    }



    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToDelete = this.publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entered ID does not exist!"));
        this.publisherRepository.delete(publisherToDelete);
        return ResponseEntity.ok(publisherToDelete);
    }
}
