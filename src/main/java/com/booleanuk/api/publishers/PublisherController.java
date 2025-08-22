package com.booleanuk.api.publishers;


import com.booleanuk.api.authors.Author;
import com.booleanuk.api.books.BookRepository;
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
    BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        return new ResponseEntity<>(publisherRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable int id) {
        Publisher publisher = this.publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        return new ResponseEntity<>(this.publisherRepository.save(publisher), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable(name = "id") int id, @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = this.publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such publisher."));
        try {
            publisherToUpdate.setName(publisher.getName());
            publisherToUpdate.setLocation(publisher.getLocation());
            return new ResponseEntity<>(this.publisherRepository.save(publisherToUpdate), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update publisher. Error: " + e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable(name = "id") int id) {
        Publisher toDelete = this.publisherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such publisher found"));
        this.bookRepository.deleteAll(toDelete.getBooks());
        this.publisherRepository.delete(toDelete);
        return ResponseEntity.ok(toDelete);

    }


}
