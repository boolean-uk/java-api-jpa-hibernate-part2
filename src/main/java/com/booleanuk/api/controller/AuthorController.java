package com.booleanuk.api.controller;


import com.booleanuk.api.model.Author;
import com.booleanuk.api.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository repository) {
        this.authorRepository = repository;
    }

    @GetMapping
    public List<Author> getAll() {
        return this.authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable int id) {
        Author author = null;
        author = this.authorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "author was not found")
        );
        return author;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author authorCreated(@RequestBody Author newAuthor) throws SQLException {
        return this.authorRepository.save(newAuthor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = this.authorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author was not found")
        );

        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.getAlive());



        return new ResponseEntity<>(this.authorRepository.save(authorToUpdate), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthorById(@PathVariable int id) {
        Author authorToDelete = this.authorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author was not found")
        );

        authorRepository.delete(authorToDelete);
        //ResponseEntity.ok stuurt een status code 200 terug, met authorToDelete als value
        //In echte projecten 'return ResponseEntity.noContent().build();' gebruiken ipv .ok
        //Dit stuurt een code 204 terug , 204 = no content
        return ResponseEntity.ok(authorToDelete);
    }




}
