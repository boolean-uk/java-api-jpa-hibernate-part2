package com.booleanuk.api.publisher;


import com.booleanuk.api.publisher.Publisher;
import com.booleanuk.api.book.Book;
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
    public List<Publisher> getAllPublishers() {
        return this.publisherRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable int id) {
        Publisher publisher = this.publisherRepository
                .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers with that id were found"));
        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        if (publisher.getName() == null || publisher.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create publisher, please check all required fields are correct");
        }
        Publisher createdPublisher = this.publisherRepository.save(publisher);
        createdPublisher.setBooks(new ArrayList<>());
        return new ResponseEntity<>(createdPublisher, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToDelete = this.getAPublisher(id);
        this.publisherRepository.delete(publisherToDelete);
        publisherToDelete.setBooks(new ArrayList<Book>());
        return ResponseEntity.ok(publisherToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisherById(@PathVariable int id, @RequestBody Publisher publisher) {

        if (publisher.getName() == null || publisher.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the publisher, please check all required fields are correct");
        }
            Publisher publisherToUpdate = null;

            publisherToUpdate = this.publisherRepository
                    .findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers with that id were found"));
            publisherToUpdate.setName(publisher.getName());
            publisherToUpdate.setLocation(publisher.getLocation());
            return new ResponseEntity<>(this.publisherRepository.save(publisherToUpdate), HttpStatus.CREATED);
        }

    private Publisher getAPublisher ( int id){
        return this.publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers with that id were found"));
    }

}