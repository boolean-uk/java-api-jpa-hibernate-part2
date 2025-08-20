package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.repositories.AuthorRepository;
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
    private AuthorRepository repository;

    @GetMapping
    public List<Author> getAll(){
        return this.repository.findAll();
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Author> getOne(@PathVariable int id){
        return new ResponseEntity<>(
                this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found")), HttpStatus.FOUND
        );
    }
    
    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author){
        return new ResponseEntity<Author>(this.repository.save(author), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author){
        Author authorToUpdate = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());

        return new ResponseEntity<>(this.repository.save(authorToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> DeleteAuthor(@PathVariable int id) {
        Author authorToDelete = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        this.repository.delete(authorToDelete);
        return ResponseEntity.ok(authorToDelete);
    }

}
