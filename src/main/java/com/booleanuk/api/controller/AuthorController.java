package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repositories.AuthorRepository;
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

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorByID(@PathVariable int id) {
        Author aut = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        return ResponseEntity.ok(aut);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(this.authorRepository.save(author));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthorByID(@PathVariable int id) {
        Author author = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        this.authorRepository.delete(author);
        author.setBooks(new ArrayList<Book>());
        return ResponseEntity.ok(author);


    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateBook(@PathVariable int id, @RequestBody Author author) {

        Author authorToUpdate = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());
        return new ResponseEntity<>(this.authorRepository.save(authorToUpdate), HttpStatus.CREATED);
    }


}
