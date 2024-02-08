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
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id) {
        Book book = bookRepository
                .findById((long) id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(book);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        checkIfBookIsValid(book);
        Author author = authorRepository
                .findById(book.getAuthor().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that ID"));
        book.setAuthor(author);

        Publisher publisher = publisherRepository
                .findById(Math.toIntExact(book.getPublisher().getId())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that ID"));
        book.setPublisher(publisher);

        return ResponseEntity.ok(bookRepository.save(book));
    }

    @PutMapping("{id}")
    public Book updateBook(@PathVariable("id") int id, @RequestBody Book book) {
        checkIfBookIsValid(book);
        Book bookToUpdate = bookRepository
                .findById((long) id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        if (bookToUpdate != null) {
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setAuthor(book.getAuthor());
            bookToUpdate.setGenre(book.getGenre());
            bookToUpdate.setPublisher(book.getPublisher());
            bookToUpdate.setAuthor(book.getAuthor());
            bookRepository.save(bookToUpdate);
        }
        return bookToUpdate;
    }

    @DeleteMapping("{id}")
    public Book deleteBook(@PathVariable("id") int id) {
        Book bookToDelete = bookRepository
                .findById((long) id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        if (bookToDelete != null) {
            bookRepository.delete(bookToDelete);
        }
        return bookToDelete;
    }

    public void checkIfBookIsValid(Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required");
        }
        if (book.getAuthor() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author is required");
        }
        if (book.getPublisher() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Publisher is required");
        }
        if (book.getGenre() == null || book.getGenre().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Genre is required");
        }
    }
}
