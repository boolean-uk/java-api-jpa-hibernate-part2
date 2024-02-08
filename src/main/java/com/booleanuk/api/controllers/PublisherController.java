package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Book;
import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.repositories.PublisherRepository;
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

    @GetMapping
    public List<Publisher> getAllPublishers()   {
        return this.publisherRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getOnePublisher(@PathVariable int id)  {
        return new ResponseEntity<>(this.publisherRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No publishers with that id were found")
                ), HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Publisher> createOnePublisher(@RequestBody Publisher publisher)   {
        if(publisher.getName() == null
        || publisher.getLocation() == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not create publisher, please check all required fields are correct");

        return new ResponseEntity<>(this.publisherRepository.save(publisher), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> updateOnePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        if(publisher.getName() == null
                || publisher.getLocation() == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not create publisher, please check all required fields are correct");
        Publisher publisherToUpdate = this.publisherRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No publishers with that id were found"
                                )
                );

        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());
        return new ResponseEntity<>(this.publisherRepository.save(publisherToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> deleteOnePublisher(@PathVariable int id)   {
        Publisher publisherToDelete = this.publisherRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No publishers with that id were found")
                );
        this.publisherRepository.delete(publisherToDelete);
        publisherToDelete.setBooks(new ArrayList<Book>());
        return ResponseEntity.ok(publisherToDelete);
    }
}
