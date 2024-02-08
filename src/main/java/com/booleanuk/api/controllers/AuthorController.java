package com.booleanuk.api.controllers;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
    public ResponseEntity<Author> create(@RequestBody AuthorDTO authorDTO) {
        if (authorDTO.first_name == null || authorDTO.last_name == null || authorDTO.email == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        Author author = new Author(authorDTO.first_name, authorDTO.last_name, authorDTO.email, authorDTO.alive);
        this.repository.save(author);
        author.setBooks(new ArrayList<>());
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Author getOne(@PathVariable int id) {
        return this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable int id, @RequestBody AuthorDTO authorDTO) {
        if (authorDTO.first_name == null || authorDTO.last_name == null || authorDTO.email == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        Author author = this.getOne(id);
        author.setFirstName(authorDTO.first_name);
        author.setLastName(authorDTO.last_name);
        author.setEmail(authorDTO.email);
        author.setAlive(authorDTO.alive);

        return new ResponseEntity<>(this.repository.save(author), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public Author delete(@PathVariable int id) {
        Author author = this.getOne(id);
        try {
            this.repository.delete(author);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Author still references a book");
        }
        author.setBooks(new ArrayList<>());
        return author;
    }
}
