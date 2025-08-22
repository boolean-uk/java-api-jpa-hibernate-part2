package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
import com.booleanuk.api.repository.PublisherRepository;
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
    private final BookRepository repository;

    @Autowired
    private final AuthorRepository authorRepository;

    @Autowired
    private final PublisherRepository publisherRepository;

    public BookController(BookRepository bookRepository, AuthorRepository ar, PublisherRepository pr) {
        this.repository = bookRepository;
        this.authorRepository = ar;
        this.publisherRepository = pr;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return new ResponseEntity<>(this.repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> put(@PathVariable int id, @RequestBody Book book) {
        Book b = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        b.setTitle(book.getTitle());
        b.setGenre(book.getGenre());
        b.setAuthor(book.getAuthor());
        b.setPublisher(book.getPublisher());
        return new ResponseEntity<>(this.repository.save(b), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> delete(@PathVariable int id) {
        Book b = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        repository.delete(b);
        return new ResponseEntity<>(b, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Author a = authorRepository.findById(book.getAuthor().getId()).orElseThrow();
        Publisher p = publisherRepository.findById((book.getPublisher().getId())).orElseThrow();

        Book b = new Book(book.getTitle(), book.getGenre(), a, p);
        return new ResponseEntity<>(repository.save(b), HttpStatus.CREATED);
    }
}
