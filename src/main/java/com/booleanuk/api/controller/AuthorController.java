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
    public List<Author> getAll() {
        return this.authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable int id) {
        Author author = null;
        author = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry this author doesn't exist"));
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author) {
        return new ResponseEntity<>(this.authorRepository.save(author), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updatedAuthor(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = this.getById(id).getBody();
        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());
        return new ResponseEntity<>(this.authorRepository.save(authorToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> authorToDelete(@PathVariable int id) {
        Author authorToDelete = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry can't delete author, with this id"));
        this.authorRepository.delete((authorToDelete));
        return ResponseEntity.ok(authorToDelete);
    }
}
