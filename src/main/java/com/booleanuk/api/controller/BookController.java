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

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private final BookRepository bookRepository;

    public BookController(BookRepository repository) {
        this.bookRepository = repository;
    }

    @GetMapping
    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book getAuthorById(@PathVariable int id) {
        Book book = null;
        book = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "author was not found")
        );
        return book;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book authorCreated(@RequestBody Book newBook) throws SQLException {
        return this.bookRepository.save(newBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateAuthor(@PathVariable int id, @RequestBody Book book) {
        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author was not found")
        );

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setAuthorID(book.getAuthorID());
        bookToUpdate.setPublisherID(book.getPublisherID());

        return new ResponseEntity<>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteAuthorById(@PathVariable int id) {
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author was not found")
        );

        bookRepository.delete(bookToDelete);
        //ResponseEntity.ok stuurt een status code 200 terug, met bookRepository als value
        //In echte projecten 'return ResponseEntity.noContent().build();' gebruiken ipv .ok
        //Dit stuurt een code 204 terug , 204 = no content
        return ResponseEntity.ok(bookToDelete);
    }


}
