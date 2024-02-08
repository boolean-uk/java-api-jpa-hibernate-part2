package com.booleanuk.api.publishers;

import com.booleanuk.api.books.Book;
import com.booleanuk.api.books.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        return ResponseEntity.ok(this.publisherRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher) {
        validatePublisherOrThrowException(publisher);

        Publisher newPublisher = this.publisherRepository.save(publisher);

        newPublisher.setBooks(new ArrayList<Book>());

        return new ResponseEntity<>(newPublisher, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable int id) {
        Publisher publisher = findPublisherOrThrowException(id);

        return ResponseEntity.ok(publisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        validatePublisherOrThrowException(publisher);

        Publisher publisherToBeUpdated = findPublisherOrThrowException(id);

        publisherToBeUpdated.setName(publisher.getName());
        publisherToBeUpdated.setLocation(publisher.getLocation());

        this.publisherRepository.save(publisherToBeUpdated);

        return new ResponseEntity<>(publisherToBeUpdated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToBeDeleted = findPublisherOrThrowException(id);

        this.publisherRepository.deleteById(id);

        publisherToBeDeleted.setBooks(new ArrayList<Book>());

        return ResponseEntity.ok(publisherToBeDeleted);
    }

    private Publisher findPublisherOrThrowException(int id) {
        return this.publisherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No publishers with that id were found."));
    }

    private void validatePublisherOrThrowException(Publisher publisher) {
        if(publisher.getName() == null || publisher.getLocation() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create/update publisher, please check all required fields are correct.");
        }
    }
}
