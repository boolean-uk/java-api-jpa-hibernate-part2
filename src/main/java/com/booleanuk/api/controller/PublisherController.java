package com.booleanuk.api.controller;

import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController()
@RequestMapping("publishers")
public class PublisherController {
    @Autowired
    PublisherRepository repo;

    @GetMapping
    public ResponseEntity<List<Publisher>> getAll() {
        return new ResponseEntity<>(
                repo.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getOne(@PathVariable(name="id") int id) {
        Publisher requested = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No authors with that id were found"
                )
        );
        return new ResponseEntity<>(
                requested,
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Publisher> createOne(@RequestBody Publisher publisher) {
        return new ResponseEntity<>(
                repo.save(publisher),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> updateOne(@PathVariable(name="id") int id, @RequestBody Publisher publisher) {
        Publisher requested = repo.findById(id)
                                .orElseThrow(() ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "No authors with that id were found"
                                        )
                                );

        requested.setName(publisher.getName());
        requested.setLocation(publisher.getLocation());

        return new ResponseEntity<>(
                repo.save(requested),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> deleteOne(@PathVariable(name="id") int id) {
        Publisher deleted = repo.findById(id)
                                .orElseThrow(() ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "No authors with that id were found"
                                        )
                                );

        repo.deleteById(id);

        return new ResponseEntity<>(
                deleted,
                HttpStatus.OK
        );
    }
}
