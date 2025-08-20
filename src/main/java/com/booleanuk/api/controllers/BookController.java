package com.booleanuk.api.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.booleanuk.api.repositories.BookRepository;
import com.booleanuk.api.models.Book;

import java.util.List;

@RestController
@RequestMapping("employees")
public class BookController {

    @Autowired
    BookRepository repository;

    @GetMapping
    public List<Book> getAllEmployees(){
        return this.repository.findAll();
    }

}
