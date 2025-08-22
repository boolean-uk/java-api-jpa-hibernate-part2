package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
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
    private AuthorRepository repository;

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author) {
        return new ResponseEntity<>(this.repository.save(author), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getById(@PathVariable int id) {
        Author author = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No author with id "+id+" found."
                )
        );
        return ResponseEntity.ok(author);
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> update(@PathVariable int id, @RequestBody Author author) {
        // Check if the specified id exist (otherwise an exception is thrown)
        this.getById(id);
        author.setId(id);
        return new ResponseEntity<>(this.repository.save(author), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> delete(@PathVariable int id) {
        // Check if the specified id exist (otherwise an exception is thrown)
        ResponseEntity<Author> authorToDelete = this.getById(id);

        try {
            this.repository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not delete author. Details: "+e.getMessage());
        }
        return authorToDelete;
    }
}
