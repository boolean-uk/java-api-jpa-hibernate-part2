package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
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
    private PublisherRepository publisherRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable int id) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Author tempAut = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author found"));
        book.setAuthor(tempAut);
        Publisher tempPub = this.publisherRepository.findById(book.getPublisher().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher found"));
        book.setPublisher(tempPub);

        return ResponseEntity.ok(this.bookRepository.save(book));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBookByID(@PathVariable int id) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        this.bookRepository.delete(book);
        return ResponseEntity.ok(book);


    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {

        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found!"));
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        Author tempAut = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author found"));
        book.setAuthor(tempAut);
        Publisher tempPub = this.publisherRepository.findById(book.getPublisher().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher found"));
        book.setPublisher(tempPub);
        return new ResponseEntity<>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

}
