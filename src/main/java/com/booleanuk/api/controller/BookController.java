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

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Author tempAuthor = this.authorRepository.findById(book.getAuthor()
                .getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "No author with that ID"));
        Publisher tempPublisher = this.publisherRepository.findById(book.getPublisher()
                .getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "No publisher with that ID"));
        book.setAuthor(tempAuthor);
        book.setPublisher(tempPublisher);
        return ResponseEntity.ok(this.bookRepository.save(book));
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getOneBook(@PathVariable int id) {
        return ResponseEntity.ok(this.getBookWithNotFound(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete = getBookWithNotFound(id);
        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book bookToUpdate = getBookWithNotFound(id);
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        Author tempAuthor = this.authorRepository.findById(book.getAuthor()
                .getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "No author with that ID"));
        Publisher tempPublisher = this.publisherRepository.findById(book.getPublisher()
                .getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "No publisher with that ID"));
        bookToUpdate.setAuthor(tempAuthor);
        bookToUpdate.setPublisher(tempPublisher);
        return new ResponseEntity<>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }


    //--------------------------- Private section---------------------------//

    private Book getBookWithNotFound(int id) {
        return this.bookRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }
}
