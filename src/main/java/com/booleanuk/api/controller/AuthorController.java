package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
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
    AuthorRepository rep;

    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.ok(rep.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getById(@PathVariable Integer id) {
        Author author = this.rep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find author by ID"));
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author) {
        return new ResponseEntity<>(this.rep.save(author), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> delete(@PathVariable Integer id) {
        Author deleteAuthor = this.rep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find author by ID"));
        this.rep.delete(deleteAuthor);
        return ResponseEntity.ok(deleteAuthor);
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> update(@PathVariable Integer id, @RequestBody Author author){
        Author oldAuthor = getById(id).getBody();

        oldAuthor.setFirstName(author.getFirstName());
        oldAuthor.setLastName(author.getLastName());
        oldAuthor.setEmail(author.getEmail());
        oldAuthor.setAlive(author.isAlive());

        return new ResponseEntity<>(this.rep.save(oldAuthor), HttpStatus.CREATED);
    }
}
