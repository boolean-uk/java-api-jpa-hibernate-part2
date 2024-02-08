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
    private AuthorRepository repository;

    @GetMapping
    public List<Author> getALlAuthors() {
        return this.repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        areAuthorValid(author);
        Author createdAuthor = this.repository.save(author);
        createdAuthor.setBooks(new ArrayList<>());
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getDepartmentById(@PathVariable int id) {
        return ResponseEntity.ok(this.getAAuthor(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        Author authorToDelete = this.getAAuthor(id);
        this.repository.delete(authorToDelete);
        authorToDelete.setBooks(new ArrayList<>());
        return ResponseEntity.ok(authorToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
        areAuthorValid(author);
        Author authorToUpdate = this.getAAuthor(id);
        authorToUpdate.setFirst_name(author.getFirst_name());
        authorToUpdate.setLast_name(author.getLast_name());
        authorToUpdate.setAlive(author.isAlive());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setBooks(author.getBooks());
        return new ResponseEntity<>(this.repository.save(authorToUpdate), HttpStatus.CREATED);
    }

    private void areAuthorValid(Author author) {
        if(author.getEmail() == null || author.getLast_name() == null || author.getFirst_name() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the author, please check all required fields are correct.");
        }
    }

    private Author getAAuthor(int id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors with that id were found"));
    }




}
