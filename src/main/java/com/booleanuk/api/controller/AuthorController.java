package com.booleanuk.api.controller;

import com.booleanuk.api.repository.AuthorRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.booleanuk.api.model.Author;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author saved = authorRepository.save(author);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable Long id) {
        return authorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        return authorRepository.findById(id).map(existing -> {
            existing.setFirstname(author.getFirstname());
            existing.setLastname(author.getLastname());
            existing.setEmail(author.getEmail());
            existing.setAlive(author.isAlive());
            authorRepository.save(existing);
            return ResponseEntity.status(201).body(existing);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        return authorRepository.findById(id).map(existing -> {
            authorRepository.delete(existing);
            return ResponseEntity.ok().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
