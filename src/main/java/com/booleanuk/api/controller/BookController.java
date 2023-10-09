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
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable int id) {
        Book book = null;
        book = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry the book with this id is not found"));
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Author author = null;
        Publisher publisher = null;
        author = this.authorRepository.findById(book.getAuthor().getAuthorId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with this id found"));
        publisher = this.publisherRepository.findById(book.getPublisher().getPublisherId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with this id found"));
        book.setAuthor(author);
        book.setPublisher(publisher);
        Book createdBook = this.bookRepository.save(book);
        return new ResponseEntity<Book>(createdBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        Author author = this.authorRepository.findById(book.getAuthor().getAuthorId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with this id found"));
        bookToUpdate.setAuthor(author);
        Publisher publisher = this.publisherRepository.findById(book.getPublisher().getPublisherId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with this id found"));
        bookToUpdate.setPublisher(publisher);
        return new ResponseEntity<>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry the book with this id can't be deleted"));
        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }
}
