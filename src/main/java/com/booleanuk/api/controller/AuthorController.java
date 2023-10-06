package com.booleanuk.api.controller;

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

    @GetMapping
    public List<Author> getAllAuthors() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Author getOneAuthor(@PathVariable int id) {
        return this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Author Not Found"));
    }

    @PostMapping
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        return new ResponseEntity<>(this.repository.save(author),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Author Not Found"));
        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());
        this.repository.save(authorToUpdate);
        return ResponseEntity.ok(authorToUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        Author author = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Author Not Found"));
        this.repository.delete(author);
        return ResponseEntity.ok(author);
    }
}
