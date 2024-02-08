package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }

    record AuthorDT0 (String first_name, String last_name, String email, boolean alive) {}
    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable int id) {
        return this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

    }


    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorDT0 authorDT0) {
        if(authorDT0.first_name == null || authorDT0.last_name == null || authorDT0.email == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad request");
        }
        Author author = new Author(authorDT0.first_name, authorDT0.last_name, authorDT0.email, authorDT0.alive);
        this.authorRepository.save(author);
        author.setBooks(new ArrayList<>());
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody AuthorDT0 authorDT0) {
        if(authorDT0.first_name == null || authorDT0.last_name == null || authorDT0.email == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad request");
        }
        Author author = this.getAuthorById(id);
        author.setFirstName(authorDT0.first_name);
        author.setLastName(authorDT0.last_name);
        author.setEmail(authorDT0.email);
        author.setAlive(authorDT0.alive);
        return new ResponseEntity<>(this.authorRepository.save(author), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public Author deleteAuthor(@PathVariable int id) {
        Author author = this.getAuthorById(id);
        try {
            this.authorRepository.delete(author);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Author still references a book");
        }
        author.setBooks(new ArrayList<>());
        return author;
    }
}