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
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @GetMapping
    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }


    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        if(containsNull(book)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create book, please check all required fields are correct.");
        }
        Author tempAuthor = this.authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors with that ID were found"));
        Publisher tempPublisher = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers with that ID were found"));
        book.setAuthor(tempAuthor);
        book.setPublisher(tempPublisher);
        return new ResponseEntity<>(bookRepository.save(book), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = findBook(id);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete = findBook(id);
        bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book bookToUpdate = findBook(id);

        if(containsNull(book)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the book, please check all required fields are correct.");
        }
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setPublisher(book.getPublisher());

        return new ResponseEntity<>(bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

    private Book findBook(int id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No books with that id were found"));
    }

    private boolean containsNull(Book book) {
        return book.getTitle() == null || book.getGenre() == null || book.getAuthor() == null || book.getPublisher() == null;
    }
}


