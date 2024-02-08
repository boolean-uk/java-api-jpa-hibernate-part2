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
    private PublisherRepository publisherRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        return ResponseEntity.ok(getABook(id));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Publisher tempPublisher = getAPublisher(book.getPublisher().getId());
        Author tempAuthor = getAnAuthor(book.getAuthor().getId());
        book.setPublisher(tempPublisher);
        book.setAuthor(tempAuthor);
        return ResponseEntity.ok(this.bookRepository.save(book));
        
        // MAYBE DELETE LATER SAVE FOR NOW
        //return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    /**
     * HELPER METHODS
     * @param id
     * @return
     */
    private Book getABook(int id) {
        return this.bookRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found!"));
    }

    private Publisher getAPublisher(int id) {
        return this.publisherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found!"));
    }

    private Author getAnAuthor(int id) {
        return this.authorRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found!"));
    }
}
