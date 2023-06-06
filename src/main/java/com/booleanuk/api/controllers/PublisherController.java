package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.repositories.PublisherRepository;
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
    PublisherRepository publisherRepo;

    @GetMapping
    public ResponseEntity<List<Publisher>> getAll() {
        return ResponseEntity.ok(this.publisherRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getById(@PathVariable int id) {
        return ResponseEntity.ok(this.publisherRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher with this id doesn't exist.")));
    }

    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher publisher) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.publisherRepo.save(publisher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> update(@PathVariable int id, @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = this.publisherRepo.findById(publisher.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher with this id doesn't exist."));

        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());

        return ResponseEntity.status(HttpStatus.CREATED).body(this.publisherRepo.save(publisherToUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> delete(@PathVariable int id) {
        Publisher publisherToDelete = this.publisherRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher with this id doesn't exist."));

        this.publisherRepo.delete(publisherToDelete);
        return ResponseEntity.ok(publisherToDelete);
    }
}
