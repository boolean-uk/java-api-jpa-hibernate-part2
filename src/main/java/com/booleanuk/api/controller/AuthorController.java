package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authors;

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author authorToCreate;
        try {
            authorToCreate = this.authors.save(author);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Create failed. Check required fields.");
        }
        return new ResponseEntity<>(authorToCreate, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return this.authors.findAll(Sort.by(Sort.Direction.ASC,"authorId"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable int id) {
        Author authorToGet = this.authors.findById(id).orElse(null);
        if (authorToGet == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with this id was not found.");
        }
        return ResponseEntity.ok(authorToGet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = this.authors.findById(id).orElse(null);
        if (authorToUpdate == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with this id was not found.");
        }
        try {
            authorToUpdate.setFirstName(author.getFirstName());
            authorToUpdate.setLastName(author.getLastName());
            authorToUpdate.setEmail(author.getEmail());
            authorToUpdate.setAlive(author.getAlive());
            authorToUpdate = this.authors.save(authorToUpdate);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Update failed. Check required fields.");
        }
        return new ResponseEntity<>(authorToUpdate, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        Author authorToDelete = this.authors.findById(id).orElse(null);
        if (authorToDelete == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with this id was not found.");
        }
        this.authors.delete(authorToDelete);
        return ResponseEntity.ok(authorToDelete);
    }
}
