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
    private AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.ok(this.authorRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getOne(@PathVariable int id) {
        Author author = null;
        author = this.authorRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with given id not found"));
        return ResponseEntity.ok(author); // 200 status by default
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author) {
        return new ResponseEntity<Author>(this.authorRepository.save(author), HttpStatus.CREATED); // 201 status
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> update(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = this.authorRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with given id not found"));

        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());

        return new ResponseEntity<Author>(this.authorRepository.save(authorToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> delete (@PathVariable int id) {
        Author authorToDelete = this.authorRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with given id not found"));

        this.authorRepository.delete(authorToDelete);

        return ResponseEntity.ok(authorToDelete); // 200 status by default
    }
}
