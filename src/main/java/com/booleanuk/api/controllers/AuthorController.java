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
}
