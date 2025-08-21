package com.booleanuk.api.controller;


import com.booleanuk.api.model.Book;
import com.booleanuk.api.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("books")
public class BooksController {

    @Autowired
    private BookRepository repository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAll() {
        return this.repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getAll(@PathVariable int id) {
        return new ResponseEntity<>(
                this.repository.findById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found")
                ), HttpStatus.OK


        );

//                this.repository.findById(id).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found")
//        );
    }

    @PostMapping
    public ResponseEntity<Book> PostUser(@Valid @RequestBody Book book) {
        return new ResponseEntity<>(this.repository.save(book), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> create(@PathVariable int id, @Valid @RequestBody Book book) {
        Book bookToUpdate = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found")
        );
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setPublisher(book.getPublisher());
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        return new ResponseEntity<>(this.repository.save(bookToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> delete( @PathVariable int id) {
        Book bookToUpdate= this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found")
        );
        this.repository.delete(bookToUpdate);
        return ResponseEntity.ok(bookToUpdate);
    }

}
