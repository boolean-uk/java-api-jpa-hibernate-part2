package com.booleanuk.api.controller;

import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
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
        bookToUpdate.setAuthor_id(book.getAuthor_id());
        bookToUpdate.setPublisher_id(book.getPublisher_id());
        return new ResponseEntity<>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

    private Book getBook(int id) {
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with that id found."));
    }
    private void checkHasRequiredValues(Book book) {
        if (book.getTitle() == null || book.getGenre() == null || book.getAuthor_id() == 0 || book.getPublisher_id() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please check all required fields are correct.");
        }
    }
}
