package com.booleanuk.api.publishers;

import com.booleanuk.api.authors.Author;
import com.booleanuk.api.authors.AuthorController;
import com.booleanuk.api.authors.AuthorRepository;
import com.booleanuk.api.authors.Publisher;
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
    private final PublisherRepository repository;

    public PublisherController(PublisherRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Publisher> getAll() {
        return this.repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getById(@PathVariable("id") int id) {
        Publisher publisher = null;
        publisher = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id was found"));
        return ResponseEntity.ok(publisher);
    }

    record PostPublisher(String name, String location) {}

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Publisher create(@RequestBody PublisherController.PostPublisher request) {
        Publisher publisher = new Publisher(request.name(), request.location());
        return this.repository.save(publisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id was found"));

        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());

        return new ResponseEntity<Publisher>(this.repository.save(publisherToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToDelete = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id was found"));
        this.repository.delete(publisherToDelete);

        return ResponseEntity.ok(publisherToDelete);
    }
}
