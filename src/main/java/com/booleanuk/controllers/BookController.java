package com.booleanuk.controllers;

import com.booleanuk.models.Author;
import com.booleanuk.models.Book;
import com.booleanuk.models.Publisher;
import com.booleanuk.repositories.AuthorRepository;
import com.booleanuk.repositories.BookRepository;
import com.booleanuk.repositories.PublisherRepository;
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
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(this.bookRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Author author = authorRepository.findById(book.getAuthor_id()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
        Publisher publisher = publisherRepository.findById(book.getPublisher_id()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found"));

        book.setAuthor(author);
        book.setPublisher(publisher);
        return new ResponseEntity<Book>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getOne(@PathVariable int id) {
        Book book = null;
        book = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable int id, @RequestBody Book book) {
        Book updated = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        Author author = authorRepository.findById(book.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id."));
        Publisher publisher = publisherRepository.findById(book.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id."));

        updated.setTitle(book.getTitle());
        updated.setGenre(book.getGenre());
        updated.setAuthor(book.getAuthor());
        updated.setPublisher(book.getPublisher());

        return new ResponseEntity<Book>(this.bookRepository.save(updated), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable int id) {
        Book toDelete = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        this.bookRepository.delete(toDelete);

        return ResponseEntity.ok(toDelete);
    }
}
