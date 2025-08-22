package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {
    @Autowired
    private final AuthorRepository repository;

    public AuthorController(AuthorRepository authorRepository) {
        this.repository = authorRepository;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        return new ResponseEntity<>(this.repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> put(@PathVariable int id, @RequestBody Author author) {
        Author a = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        a.setFirstName(author.getFirstName());
        a.setLastName(author.getLastName());
        a.setEmail(author.getEmail());
        a.setAlive(author.isAlive());
        return new ResponseEntity<>(this.repository.save(a), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> delete(@PathVariable int id) {
        Author a = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        repository.delete(a);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author) {
        Author a = new Author(author.getFirstName(), author.getLastName(), author.getEmail(), author.isAlive());
        return new ResponseEntity<>(repository.save(a), HttpStatus.CREATED);
    }
}
