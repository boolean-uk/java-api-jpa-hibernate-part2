package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@RequestMapping("authors")
public class AuthorController {
    private final AuthorRepository repository;

    @Autowired // Prefer constructor injection to field injection
    public AuthorController(AuthorRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author add(@RequestBody Author author) {
        checkIfValidObject(author);
        return this.repository.save(author);
    }

    @GetMapping
    public List<Author> getAll() {
        return this.repository.findAll();
    }

    @GetMapping("{id}")
    public Author getById(@PathVariable int id) {
        return this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Author updateById(@PathVariable int id, @RequestBody Author author) {
        checkIfValidObject(author);
        Author authorToUpdate = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.getAlive());

        return this.repository.save(authorToUpdate);
    }

    @DeleteMapping("{id}")
    public Author deleteById(@PathVariable int id) {
        Author authorToDelete = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.repository.delete(authorToDelete);
        return authorToDelete;
    }

    private void checkIfValidObject(Author author) {
        if (Stream.of(author.getFirstName(), author.getLastName(), author.getEmail(), author.getAlive())
                .anyMatch(Objects::isNull)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create object. Required fields are NULL.");
        }
        if (Stream.of(author.getFirstName(), author.getLastName(), author.getEmail())
                .anyMatch(String::isBlank)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create object. Required fields are empty.");
        }
    }
}
