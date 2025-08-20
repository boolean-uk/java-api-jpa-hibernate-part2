package com.booleanuk.controllers;

import com.booleanuk.models.Author;
import com.booleanuk.repositories.AuthorRepository;
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
    private AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.ok(this.authorRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author) {
        return new ResponseEntity<Author>(this.authorRepository.save(author), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getOne(@PathVariable int id) {
        Author author = null;
        author = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable int id, @RequestBody Author author) {
        Author updated = this.authorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        updated.setFirstName(author.getFirstName());
        updated.setLastName(author.getLastName());
        updated.setEmail(author.getEmail());
        updated.setAlive(author.isAlive());

        return new ResponseEntity<Author>(this.authorRepository.save(updated), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> delete(@PathVariable int id) {
        Author toDelete = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        this.authorRepository.delete(toDelete);

        return ResponseEntity.ok(toDelete);
    }
}
