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
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") int id) {
        Author author = authorRepository
                .findById((long) id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        checkIfAuthorIsValid(author);
        Author newAuthor = authorRepository.save(author);
        return ResponseEntity.ok(newAuthor);
    }

    @PutMapping("{id}")
    public Author updateAuthor(@PathVariable("id") int id, @RequestBody Author author) {
        checkIfAuthorIsValid(author);
        Author authorToUpdate = authorRepository
                .findById((long) id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        if (authorToUpdate != null) {
            authorToUpdate.setFirstName(author.getFirstName());
            authorToUpdate.setLastName(author.getLastName());
            authorRepository.save(authorToUpdate);
        }
        return authorToUpdate;
    }

    @DeleteMapping("{id}")
    public Author deleteAuthor(@PathVariable("id") int id) {
        Author authorToDelete = authorRepository
                .findById((long) id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        if (authorToDelete != null) {
            authorRepository.delete(authorToDelete);
        }
        return authorToDelete;
    }

    public void checkIfAuthorIsValid(Author author) {
        if (author.getFirstName() == null || author.getLastName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author must have a first and last name");
        }
    }
}
