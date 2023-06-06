package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.models.Book;
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
    AuthorRepository authorRepo;

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.ok(this.authorRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable int id) {
        return ResponseEntity.ok(this.authorRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with this id doesn't exist.")));
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.authorRepo.save(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = this.authorRepo.findById(author.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with this id doesn't exist."));

        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setAlive(author.isAlive());
        authorToUpdate.setEmail(author.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(this.authorRepo.save(authorToUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> delete(@PathVariable int id) {
        Author authorToDelete = this.authorRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with this id doesn't exist."));

        this.authorRepo.delete(authorToDelete);
        return ResponseEntity.ok(authorToDelete);
    }
}
