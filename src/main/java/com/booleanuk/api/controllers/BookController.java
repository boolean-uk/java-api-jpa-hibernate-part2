package com.booleanuk.api.controllers;

import com.booleanuk.api.model.Book;
import com.booleanuk.api.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    BookRepository repository;

    @GetMapping
    public List<Book> getAll() {
        return this.repository.findAll();
    }
}
