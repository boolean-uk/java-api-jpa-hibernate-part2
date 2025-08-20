package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.models.Book;
import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.repositories.AuthorRepository;
import com.booleanuk.api.repositories.BookRepository;
import com.booleanuk.api.repositories.PublisherRepository;
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
    public PublisherRepository publisherRepository;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(this.bookRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getOne(@PathVariable int id) {
        Book book = null;
        book = this.bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with given id not found"));
        return ResponseEntity.ok(book); // 200 status by default
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Author author = authorRepository.findById(book.getAuthor_id())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Publisher publisher = publisherRepository.findById(book.getPublisher_id())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));

        book.setAuthor(author);
        book.setPublisher(publisher);
        return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Author author = authorRepository.findById(book.getAuthor_id())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Publisher publisher = publisherRepository.findById(book.getPublisher_id())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));

        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No books with that id were found to update"));

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setAuthor(author);
        bookToUpdate.setPublisher(publisher);

        return new ResponseEntity<>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> delete (@PathVariable int id) {
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with given id not found"));

        this.bookRepository.delete(bookToDelete);

        return ResponseEntity.ok(bookToDelete); // 200 status by default
    }
}
