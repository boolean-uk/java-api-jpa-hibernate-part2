package com.booleanuk.api.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.booleanuk.api.repositories.AuthorRepository;
import com.booleanuk.api.models.Author;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {
    
    @Autowired
    AuthorRepository repository;
    
    @GetMapping
    public List<Author> getAllDepartments(){
        return this.repository.findAll();
    }
    
}
