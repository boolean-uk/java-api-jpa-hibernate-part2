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
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable int id) {
        return ResponseEntity.ok(this.getBook(id));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        this.checkHasRequiredValues(book);
        book.setAuthor_id(this.getTempAuthor(book));
        book.setPublisher_id(this.getTempPublisher(book));
        return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete = this.getBook(id);
        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        this.checkHasRequiredValues(book);
        Book bookToUpdate = this.getBook(id);
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setAuthor_id(this.getTempAuthor(book));
        bookToUpdate.setPublisher_id(this.getTempPublisher(book));
        return new ResponseEntity<>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

    private Book getBook(int id) {
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with that id found."));
    }
    private void checkHasRequiredValues(Book book) {
        if (book.getTitle() == null || book.getGenre() == null || book.getAuthor_id().getId() == 0 || book.getPublisher_id().getId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please check all required fields are correct.");
        }
    }

    private Author getTempAuthor(Book book) {
        return this.authorRepository.findById(book.getAuthor_id().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id found."));
    }

    private Publisher getTempPublisher(Book book) {
        return this.publisherRepository.findById(book.getPublisher_id().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id found."));
    }
}
