package com.booleanuk.api.controllers;

import com.booleanuk.api.modules.Author;
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
    public List<Author> getAll(){
        return this.authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable int id){
        Author author = getAnAuthor(id);
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<Author> addAuthor(@RequestBody Author author){
        Author createAuthor = this.authorRepository.save(author);
        return new ResponseEntity<Author>(createAuthor,
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author){
        Author updateAuthor = getAnAuthor(id);
        updateAuthor.setFirst_name(author.getFirst_name());
        updateAuthor.setLast_name(author.getLast_name());
        updateAuthor.setEmail(author.getEmail());
        updateAuthor.setAlive(author.isAlive());
        return new ResponseEntity<Author>(this.authorRepository.save(updateAuthor),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id){
        Author deleteAuthor = getAnAuthor(id);
        this.authorRepository.delete(deleteAuthor);
        deleteAuthor.setBooks(new ArrayList<>());
        return ResponseEntity.ok(deleteAuthor);
    }

    private Author getAnAuthor(int id){
        return this.authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
