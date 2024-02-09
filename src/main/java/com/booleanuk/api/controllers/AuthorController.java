package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.repositories.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {
    record PostAuthor(String first_name, String last_name, String email, Boolean alive) {}

    @Autowired
    private AuthorRepo authorRepository;

    @GetMapping
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getById(@PathVariable final Integer id) {
        return new ResponseEntity<>(authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody PostAuthor request) {
        return new ResponseEntity<>(authorRepository.save(new Author(request.first_name, request.last_name, request.email, request.alive)), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> update(@PathVariable final Integer id, @RequestBody final Author author) {
        Author _targetAuthor = authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found."));

        _targetAuthor.setFirst_name(author.getFirst_name());
        _targetAuthor.setLast_name(author.getLast_name());
        _targetAuthor.setEmail(author.getEmail());
        _targetAuthor.setAlive(author.getAlive());

        return new ResponseEntity<>(authorRepository.save(_targetAuthor), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> remove(@PathVariable final Integer id) {
        return new ResponseEntity<>(authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")), HttpStatus.OK);
    }
}
