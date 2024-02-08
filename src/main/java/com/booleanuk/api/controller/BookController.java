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
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        return ResponseEntity.ok(this.getABook(id));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        if (book.getTitle() == null || book.getGenre() == null ||
                book.getAuthor() == null || book.getPublisher() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "Could not create the book, please check all required fields");
        }

        Author tempAuthor = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors matching that id were found"));
        Publisher tempPublisher = this.publisherRepository.findById(book.getPublisher().getId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers matching that id were found"));

        book.setAuthor(tempAuthor);
        book.setPublisher(tempPublisher);

        return new ResponseEntity<Book>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable int id, @RequestBody Book book) {
        if (book.getTitle() == null || book.getGenre() == null ||
                book.getAuthor() == null || book.getPublisher() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , "Could not update the book's details, please check all required fields");
        }

        Author tempAuthor = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors matching that id were found"));
        Publisher tempPublisher = this.publisherRepository.findById(book.getPublisher().getId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers matching that id were found"));

        book.setAuthor(tempAuthor);
        book.setPublisher(tempPublisher);

        Book bookToUpdate = this.getABook(id);
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setPublisher(book.getPublisher());
        return new ResponseEntity<Book>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBookById(@PathVariable int id) {
        Book bookToDelete = this.getABook(id);
        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }

    private Book getABook(int id) {
        return this.bookRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No books matching that id were found"));
    }

}
