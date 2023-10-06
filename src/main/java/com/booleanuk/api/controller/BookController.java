package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepo;
import com.booleanuk.api.repository.BookRepo;
import com.booleanuk.api.repository.PublisherRepo;
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
    private BookRepo bookRepo;
    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private PublisherRepo publisherRepo;

    @GetMapping
    public List<Book> getAllBooks() {
        return this.bookRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id) {
        try {
            Book book = this.bookRepo.findById(id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        try {
            Author author = this.authorRepo.findById(book.getAuthor().getId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
            Publisher publisher = this.publisherRepo.findById(book.getPublisher().getId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
            book.setAuthor(author);
            book.setPublisher(publisher);

            return new ResponseEntity<>(this.bookRepo.save(book), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@RequestBody Book book, @PathVariable int id) {
        try {
           Book updatedBook = this.bookRepo.findById(id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
           updatedBook.setTitle(book.getTitle());
           updatedBook.setGenre(book.getGenre());
           if (updatedBook.getAuthor().getId() != book.getAuthor().getId()) {
               Author author = this.authorRepo.findById(book.getAuthor().getId()).orElseThrow(() ->
                       new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
               updatedBook.setAuthor(author);
           }
            if (updatedBook.getPublisher().getId() != book.getPublisher().getId()) {
                Publisher publisher = this.publisherRepo.findById(book.getPublisher().getId()).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
                updatedBook.setPublisher(publisher);
            }
            return new ResponseEntity<>(this.bookRepo.save(updatedBook), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        try {
            Book deletedBook = this.bookRepo.findById(id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
            this.bookRepo.delete(deletedBook);
            return new ResponseEntity<>(deletedBook, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);
        }
    }


}