package com.booleanuk.api.controller;

import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.BookRepository;
import com.booleanuk.api.repository.PublisherRepository;
import jakarta.validation.Valid;
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

    @Autowired
    private BookRepository bookRepository;


    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        List<Publisher> result = this.publisherRepository.findAll();
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable int id) {
        return this.publisherRepository.findById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No publisher with that id was found"
                ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Publisher createPublisher(@Valid @RequestBody Publisher body) {
        return this.publisherRepository.save(body);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @Valid @RequestBody Publisher body) {
        return this.publisherRepository.findById(id).map(author -> {
            author.setName(body.getName());
            author.setLocation(body.getLocation());
            author.setBooks(body.getBooks());

            this.publisherRepository.save(author);
            return ResponseEntity.status(HttpStatus.CREATED).body(author);

        }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No publisher with that ID was found"
        ));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        return this.publisherRepository.findById(id).map(publisher -> {

            publisher.getBooks().forEach(book -> {
                book.setPublisher(null);
                this.bookRepository.save(book);
            });

            this.publisherRepository.delete(publisher);
            return ResponseEntity.ok(publisher);
        }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No publisher with that ID was found"
        ));
    }
}
