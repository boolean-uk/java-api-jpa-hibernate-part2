package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
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
//    @Autowired
//    private BookRepository bookRepository;

//    public AuthorController(AuthorRepository authorRepository) {
//        this.authorRepository = authorRepository;
//    }

    @GetMapping
    public List<Author> getAll() {
        return this.authorRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getById(@PathVariable int id) {
        Author author = this.authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        return ResponseEntity.ok(author);
    }

//    record PostAuthor(String first_name, String last_name, String email, boolean alive) {}

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author) {
        return ResponseEntity.ok(this.authorRepository.save(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = this.authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        authorToUpdate.setFirst_name(author.getFirst_name());
        authorToUpdate.setLast_name(author.getLast_name());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());
        authorToUpdate.setBooks(new ArrayList<>());

        return new ResponseEntity<Author>(authorRepository.save(authorToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        Author authorToDelete = this.authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.authorRepository.delete(authorToDelete);

        return ResponseEntity.ok(authorToDelete);
    }
}
