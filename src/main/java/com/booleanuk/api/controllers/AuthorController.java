package com.booleanuk.api.controllers;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {
    @Autowired
    AuthorRepository repository;

    @GetMapping
    public List<Author> getAll() {
        return this.repository.findAll();
    }

    record AuthorDTO (String first_name, String last_name, String email, boolean alive) {}

    @PostMapping
    public Author create(@RequestBody AuthorDTO author) {
        return this.repository.save(new Author(author.first_name, author.last_name, author.email, author.alive));
    }
}
