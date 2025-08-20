package com.booleanuk.api.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.booleanuk.api.repositories.AuthorRepository;
import com.booleanuk.api.models.Author;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {
    
    @Autowired
    AuthorRepository repo;

    @GetMapping
    public ResponseEntity<List<Author>> getAll(){
        return ResponseEntity.ok(this.repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> get(@PathVariable int id){
        Author author = this.repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This is not found by dave!"));
        return ResponseEntity.ok(author);
    }
    @PostMapping
    public ResponseEntity<Author> add(@RequestBody Author author){
        return new ResponseEntity<>(this.repo.save(author), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update( @PathVariable int id,@RequestBody Author author){
        Author empToUpdate = this.repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This is not found by dave!"));
        empToUpdate.setFirstName(author.getFirstName());
        empToUpdate.setLastName(author.getLastName());
        empToUpdate.setAlive(author.getAlive());
        empToUpdate.setEmail(author.getEmail());

        return new ResponseEntity<>(this.repo.save(empToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> delete(@PathVariable int id){
        Author empToBeDeleted = this.repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This is not found by dave!"));
        this.repo.delete(empToBeDeleted);
        return ResponseEntity.ok(empToBeDeleted);
    }


}
