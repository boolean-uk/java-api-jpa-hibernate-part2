package com.booleanuk.api.controller;
import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
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
    public ResponseEntity<Author> getById(@PathVariable int id){
        Author employee = this.authorRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        return ResponseEntity.ok(employee);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> delete(@PathVariable int id) {
        Author toDelete = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No publisher with ID"));
        this.authorRepository.delete(toDelete);
        toDelete.setBooks(new ArrayList<Book>());
        return ResponseEntity.ok(toDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable int id, @RequestBody Author employee){
        Author update = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        update.setFirstName(employee.getFirstName());
        update.setLastName(employee.getLastName());
        update.setEmail(employee.getEmail());
        update.setAlive(employee.isAlive());
        update.setBooks(new ArrayList<>());
        return new ResponseEntity<>(this.authorRepository.save(update), HttpStatus.CREATED);
    }
}
