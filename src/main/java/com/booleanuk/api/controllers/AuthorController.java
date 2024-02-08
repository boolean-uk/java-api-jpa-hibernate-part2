package com.booleanuk.api.controllers;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.repositories.AuthorRepository;
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
    AuthorRepository repository;

    @GetMapping
    public List<Author> getAll() {
        return this.repository.findAll();
    }

    record AuthorDTO (String first_name, String last_name, String email, boolean alive) {}

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody AuthorDTO author) {
        if (author.first_name == null || author.last_name == null || author.email == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        return new ResponseEntity<>(this.repository.save(new Author(author.first_name, author.last_name, author.email, author.alive)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Author getOne(@PathVariable int id) {
        return this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
    }
}
