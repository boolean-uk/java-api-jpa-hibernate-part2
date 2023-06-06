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
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = null;
        book = this.bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Author tempAuthor = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't create book as author id not found"));
        book.setAuthor(tempAuthor);
        Publisher tempPublisher = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't create book " +
                        "as publisher id not found"));
        book.setPublisher(tempPublisher);
        return new ResponseEntity<Book>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't update as id not found"));

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());

        Author tempAuthor = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't create book as author id not found"));
        bookToUpdate.setAuthor(tempAuthor);

        Publisher tempPublisher = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't create book " +
                        "as publisher id not found"));
        bookToUpdate.setPublisher(tempPublisher);

        return new ResponseEntity<Book>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't delete as id not found"));
        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }
}
