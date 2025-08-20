package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.models.Book;
import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.repositories.AuthorRepsitory;
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
    private AuthorRepsitory authorRepsitory;

    @Autowired
    private PublisherRepository publisherRepository;


    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(this.bookRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Author author = authorRepsitory.findById(book.getAuthor_id())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Publisher publisher = publisherRepository.findById(book.getPublisher_id())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));

        book.setAuthor(author);
        book.setPublisher(publisher);
        return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No books with that id were found"));
        return ResponseEntity.ok(book);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Author author = authorRepsitory.findById(book.getAuthor_id())
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
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No books with that id were found to delete"));

        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);

    }

}
