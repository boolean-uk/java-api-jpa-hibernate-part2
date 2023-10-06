package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.repository.AuthorRepo;
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
    private AuthorRepo authorRepo;

    @GetMapping
    public List<Author> getAllAuthors() {
        return this.authorRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable int id) {
        try {
            Author author = this.authorRepo.findById(id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
            return ResponseEntity.ok(author);
        } catch (Exception e) {
            return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody Author author) {
        try {
            return new ResponseEntity<>(this.authorRepo.save(author), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable int id, @RequestBody Author author) {
        try {
            Author updatedAuthor = this.authorRepo.findById(id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
            updatedAuthor.setFirstName(author.getFirstName());
            updatedAuthor.setLastName(author.getLastName());
            updatedAuthor.setEmail(author.getEmail());
            updatedAuthor.setAlive(author.isAlive());
            return new ResponseEntity<>(this.authorRepo.save(updatedAuthor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable int id) {
        try {
            Author deletedAuthor = this.authorRepo.findById(id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
            this.authorRepo.delete(deletedAuthor);
            return ResponseEntity.ok(deletedAuthor);
        } catch (Exception e) {
            return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
        }
    }
}
