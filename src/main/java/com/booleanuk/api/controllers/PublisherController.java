package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.repositories.PublisherRepo;
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

    @Autowired
    private PublisherRepo publisherRepo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Publisher> getAllPublishers() {
        List<Publisher> publishers = publisherRepo.findAll();

        if(publishers.size() == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers were found");

        return publishers;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Publisher getPublisherById(@PathVariable int id){
        Publisher publisher;
        publisher = publisherRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher by that id was found"));

        return publisher;

    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        try {
            return new ResponseEntity<>(publisherRepo.save(publisher), HttpStatus.CREATED);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create the new publisher, please check all required fields are correct.");
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = publisherRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers matching that id were found"));

        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());

        try {
            return new ResponseEntity<>(publisherRepo.save(publisherToUpdate), HttpStatus.CREATED);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the publisher's details, please check all required fields are correct.");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToDelete = publisherRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers matching that id were found"));
        publisherRepo.delete(publisherToDelete);
        return ResponseEntity.ok(publisherToDelete);
    }

}
