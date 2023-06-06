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

    public AuthorController(AuthorRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    public List<Author> getAll() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable("id") Integer id) {
        return this.repository.findById(id).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        return new ResponseEntity<Author>(this.repository.save(author), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Integer id, @RequestBody Author author) {
        Author authorToUpdate = this.repository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        authorToUpdate.setFirst_name(author.getFirst_name());
        authorToUpdate.setLast_name(author.getFirst_name());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());

        return new ResponseEntity<Author>(this.repository.save(authorToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable Integer id) {
        Author authorToDelete = this.repository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.repository.delete(authorToDelete);
        return ResponseEntity.ok(authorToDelete);
    }
}
