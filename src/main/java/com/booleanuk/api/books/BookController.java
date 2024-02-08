package com.booleanuk.api.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepository books;

    @GetMapping
    public List<Book> getAll(){

    }

    @GetMapping
    public ResponseEntity<Book> getOne(){

    }

    @PostMapping
    public ResponseEntity<Book> create(){

    }

    @PutMapping
    public ResponseEntity<Book> update(){

    }

    @DeleteMapping
    public ResponseEntity<Book> delete(){

    }
}
