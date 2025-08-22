package com.booleanuk.publishers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("publishers")
public class PublisherController {
    @Autowired
    private PublisherRepository repo;

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        return ResponseEntity.ok(this.repo.findAll());
    }

    @PostMapping
    public ResponseEntity<Publisher> addPublisher(@RequestBody Publisher publisher) {
        return new ResponseEntity<Publisher>(this.repo.save(publisher), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getOnePublisher(@PathVariable int id) {
        return ResponseEntity.ok(this.repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find publisher with this id")));
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = this.repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find publisher with this id"));
        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());

        return new ResponseEntity<Publisher>(this.repo.save(publisherToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {
        Publisher publisherToDelete = this.repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find publisher with this id"));

        this.repo.delete(publisherToDelete);
        return ResponseEntity.ok(publisherToDelete);
    }
}
