package com.booleanuk.api.controller;

import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.BookTDO;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository books;
    @Autowired
    private AuthorRepository authors;
    @Autowired
    private PublisherRepository publishers;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookTDO book) {
        Book bookToCreate = new Book();
        bookToCreate.setTitle(book.title());
        bookToCreate.setGenre(book.genre());
        bookToCreate.setAuthor(this.authors.findById(book.authorId()).orElse(null));
        bookToCreate.setPublisher(this.publishers.findById(book.publisherId()).orElse(null));
        try {
            bookToCreate = this.books.save(bookToCreate);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Create failed. Check required fields." + e.getMessage());
        }
        return new ResponseEntity<>(bookToCreate, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return this.books.findAll(Sort.by(Sort.Direction.ASC,"bookId"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable int id) {
        Book bookToGet = this.books.findById(id).orElse(null);
        if (bookToGet == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with this id was not found.");
        }
        return ResponseEntity.ok(bookToGet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book bookToUpdate = this.books.findById(id).orElse(null);
        if (bookToUpdate == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with this id was not found.");
        }
        try {
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setGenre(book.getGenre());
            bookToUpdate.setAuthor(book.getAuthor());
            bookToUpdate.setPublisher(book.getPublisher());
            bookToUpdate = this.books.save(bookToUpdate);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Update failed. Check required fields.");
        }
        return new ResponseEntity<>(bookToUpdate, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete = this.books.findById(id).orElse(null);
        if (bookToDelete == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with this id was not found.");
        }
        this.books.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }
}
