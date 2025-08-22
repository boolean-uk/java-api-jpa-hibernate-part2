package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.models.Book;
import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.repositorys.AuthorRepository;
import com.booleanuk.api.repositorys.BookRepository;
import com.booleanuk.api.repositorys.PublisherRepository;
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
    public BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(this.bookRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getById(@PathVariable("id") Integer id) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        System.out.println(book);
        Author author = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author not found"));
        book.setAuthor(author);
        Publisher publisher = this.publisherRepository.findById(book.getPublisher().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Publisher not found"));
        book.setPublisher(publisher);
        System.out.println(book);
        return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> update(@PathVariable("id") Integer id, @RequestBody Book book) {
        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        Author author = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author not found"));
        Publisher publisher = this.publisherRepository.findById(book.getPublisher().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Publisher not found"));
        bookToUpdate.setAuthor(author);
        bookToUpdate.setPublisher(publisher);
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());

        return new ResponseEntity<>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteUser(@PathVariable("id") Integer id) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        this.bookRepository.delete(book);
        return ResponseEntity.ok(book);
    }
}
