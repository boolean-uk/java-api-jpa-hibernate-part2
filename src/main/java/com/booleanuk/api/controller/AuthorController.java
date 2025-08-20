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
@RequestMapping("authors")
public class AuthorController {

    ///////////////

    @Autowired
    private AuthorRepository repository;

    public AuthorController(AuthorRepository repository) {
        this.repository = repository;
    }

    /*@GetMapping
    public List<Author> getAll() {
        return this.repository.findAll();
    }*/

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("{id}")
    public Author getById(@PathVariable("id") Integer id) {
        return this.repository.findById(id).orElseThrow();
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        return new ResponseEntity<>(this.repository.save(author), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id,
                                           @RequestBody Author author) {
        Author authorToUpdate = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existing author with that ID found")
        );
        authorToUpdate.setFirst_name(author.getFirst_name());
        authorToUpdate.setLast_name(author.getLast_name());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.getAlive());
        return new ResponseEntity<>(this.repository.save(authorToUpdate),
                HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        Author authorToDelete = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No authors with that ID were found")
        );
        this.repository.delete(authorToDelete);
        return ResponseEntity.ok(authorToDelete);
    }
}

