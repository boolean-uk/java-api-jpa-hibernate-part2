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
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable int id) {
        Author author = authorRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors with that id were found"));
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        if(
            author.getAlive() == null ||
            author.getFirst_name() == null ||
            author.getLast_name() == null ||
            author.getEmail() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not create author, please check all required fields are correct");
        }
        return ResponseEntity.ok(authorRepository.save(author));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
        Author existingAuthor = authorRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors with that id were found"));
        existingAuthor.setFirst_name(author.getFirst_name());
        existingAuthor.setLast_name(author.getLast_name());
        existingAuthor.setEmail(author.getEmail());
        existingAuthor.setAlive(author.getAlive());
        if(
            existingAuthor.getAlive() == null ||
            existingAuthor.getFirst_name() == null ||
            existingAuthor.getLast_name() == null ||
            existingAuthor.getEmail() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not update author, please check all required fields are correct");
        }
        return ResponseEntity.ok(authorRepository.save(existingAuthor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        Author author = authorRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id were found"));
        authorRepository.delete(author);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }
}
