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
    private AuthorRepository repository;

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author) {
        return new ResponseEntity<Author>(this.repository.save(author), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Author> getAll() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable int id) {
        Author author = null;
        author = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        authorToUpdate.setFirst_name(author.getFirst_name());
        authorToUpdate.setLast_name(author.getLast_name());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());

        return new ResponseEntity<Author>(this.repository.save(authorToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> delete(@PathVariable int id) {
        Author authorToDelete = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.repository.delete(authorToDelete);
        return ResponseEntity.ok(authorToDelete);
    }

}
