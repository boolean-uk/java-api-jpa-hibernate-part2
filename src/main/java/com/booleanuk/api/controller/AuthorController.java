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

    @Autowired
    private AuthorRepository authors;

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author toAdd) {
        return new ResponseEntity<>(this.authors.save(toAdd), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        return new ResponseEntity<>(this.authors.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getOne(@PathVariable int id) {
        Author author = this.authors.findById(id).
                orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")
                );
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> delete(@PathVariable int id) {
        Author toDelete = this.authors.findById(id).
                orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")
                );
        this.authors.delete(toDelete);
        return new ResponseEntity<>(toDelete, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author newData) {
        Author authorToUpdate = this.authors.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")
                );

        authorToUpdate.setFirstName(newData.getFirstName());
        authorToUpdate.setLastName(newData.getLastName());
        authorToUpdate.setEmail(newData.getEmail());
        authorToUpdate.setAlive(newData.isAlive());

        return new ResponseEntity<>(this.authors.save(authorToUpdate), HttpStatus.OK);
    }


}
