package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.models.Book;
import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.repositories.AuthorRepository;
import com.booleanuk.api.repositories.BookRepository;
import com.booleanuk.api.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepository repository;

    @Autowired
    private AuthorRepository associatedAuthorRepository;

    @Autowired
    private PublisherRepository associatedPublisherRepository;

    private Author getAuthorObject(int id) {
        Author author = this.associatedAuthorRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Not found")
                );
        return author;
    }

    private Publisher getPublisherObject(int id) {
        Publisher publisher = this.associatedPublisherRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Not found.")
                );
        return publisher;
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Author author = this.getAuthorObject(book.getAuthor().getId());
        book.setAuthor(author);

        Publisher publisher = this.getPublisherObject(book.getPublisher().getId());
        book.setPublisher(publisher);

        return new ResponseEntity<>(this.repository.save(book), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getById(@PathVariable int id) {
        Book book = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No book with id "+id+" found.")
        );
        return ResponseEntity.ok(book);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> update(@PathVariable int id, @RequestBody Book book) {

        Author author = this.getAuthorObject(book.getAuthor().getId());
        book.setAuthor(author);

        Publisher publisher = this.getPublisherObject(book.getPublisher().getId());
        book.setPublisher(publisher);

        book.setId(id);

        return new ResponseEntity<>(this.repository.save(book), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> delete(@PathVariable int id) {
        // check if id exist
        ResponseEntity<Book> book = this.getById(id);
        this.repository.deleteById(id);
        return book;
    }
}
