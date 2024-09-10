package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
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
    private BookRepository repository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Book> getAll() {
        return this.repository.findAll();
    }

    @GetMapping("{id}")
    public Book getById(@PathVariable("id") Integer id) {
        return this.repository.findById(id).orElseThrow();
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Author author = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author found..")
        );
        book.setAuthor(author);
        Publisher publisher = this.publisherRepository.findById(book.getPublisher().getId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author found..")
        );
        book.setPublisher(publisher);
        return new ResponseEntity<>(this.repository.save(book), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id,
                                           @RequestBody Book book) {
        Book bookToUpdate = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existing book with that ID found")
        );
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        return new ResponseEntity<>(this.repository.save(bookToUpdate),
                HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete = this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No books with that ID were found")
        );
        this.repository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }
}

