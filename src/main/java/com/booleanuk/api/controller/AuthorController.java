package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController()
@RequestMapping("authors")
public class AuthorController {
    @Autowired
    AuthorRepository repo;

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        return new ResponseEntity<>(
                repo.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getOne(@PathVariable(name="id") int id) {
        Author requested = repo.findById(id).orElseThrow(() ->
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
    public ResponseEntity<Author> createOne(@RequestBody Author author) {
        return new ResponseEntity<>(
                repo.save(author),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> updateOne(@PathVariable(name="id") int id, @RequestBody Author author) {
        Author requested = repo.findById(id)
                                .orElseThrow(() ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "No authors with that id were found"
                                        )
                                );

        requested.setFirstName(author.getFirstName());
        requested.setFirstName(author.getLastName());
        requested.setEmail(author.getEmail());
        requested.setAlive(author.isAlive());

        return new ResponseEntity<>(
                repo.save(requested),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> deleteOne(@PathVariable(name="id") int id) {
        Author deleted = repo.findById(id)
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
