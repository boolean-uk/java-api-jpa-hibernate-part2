package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.repositories.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {
    record PostAuthor(String firstName, String lastName, String email, Boolean isAlive) {}

    @Autowired
    final AuthorRepo repository;

    public AuthorController(AuthorRepo repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Author> getAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getById(@PathVariable final Integer id) {
        return new ResponseEntity<>(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody PostAuthor request) {
        return new ResponseEntity<>(repository.save(new Author(request.firstName, request.lastName, request.email, request.isAlive)), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> update(@PathVariable final Integer id, @RequestBody final Author author) {
        Author _targetAuthor = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found."));

        _targetAuthor.firstName = author.firstName;
        _targetAuthor.lastName = author.lastName;
        _targetAuthor.email = author.email;
        _targetAuthor.isAlive = author.isAlive;

        return new ResponseEntity<>(repository.save(_targetAuthor), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> remove(@PathVariable final Integer id) {
        return new ResponseEntity<>(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")), HttpStatus.OK);
    }
}
