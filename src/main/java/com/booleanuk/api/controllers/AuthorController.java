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
    private AuthorRepository authorRepository;

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author){
        return new ResponseEntity<>(this.authorRepository.save(author), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Author> getAll(){
        return this.authorRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getOne(@PathVariable int id){
        Author author = this.authorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id")
        );
        return ResponseEntity.ok(author);
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> update(@PathVariable int id, @RequestBody Author author){
        Author authorToUpdate = this.authorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id")
        );
        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());
        return new ResponseEntity<>(this.authorRepository.save(authorToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> delete(@PathVariable int id){
        Author authorToDelete = this.authorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id")
        );
        this.authorRepository.delete(authorToDelete);
        return ResponseEntity.ok(authorToDelete);
    }
}
