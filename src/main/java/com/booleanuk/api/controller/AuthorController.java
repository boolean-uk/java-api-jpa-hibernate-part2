package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Publisher;
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
    private final AuthorRepository repository;


    public AuthorController (AuthorRepository repository){this.repository = repository;}

    @GetMapping
    public List<Author> getAllAuthors(){
        return this.repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author){
        return new ResponseEntity<Author>(this.repository.save(author), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable int id){
        Author author = null;
        author = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that ID found")
        );
        return ResponseEntity.ok(author);
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author){
        Author authorToUpdate = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Author with that ID found")
        );
        author.setId(authorToUpdate.getId());
        return new ResponseEntity<>(this.repository.save(author), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id){
        Author authorToDelete = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No author with that ID found")
        );
        this.repository.delete(authorToDelete);
        return ResponseEntity.ok(authorToDelete);
    }




}
