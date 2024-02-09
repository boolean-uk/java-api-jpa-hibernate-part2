package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.BookReq;
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
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable int id) {
        Book book = bookRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No books with that id were found"));
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> createBook(@RequestBody BookReq book) {
        System.out.println(book);
        Author author = authorRepository
            .findById(book.getAuthor_id())
            .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors with that id were found"));
        Publisher publisher = publisherRepository
            .findById(book.getPublisher_id())
            .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors with that id were found"));
        Book resultBook = new Book(book.getTitle(), book.getGenre(), author, publisher);
        if(book.getGenre() == null || book.getTitle() == null || author == null || publisher == null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not create book, please check all required fields are correct");
        }
        return ResponseEntity.ok(bookRepository.save(resultBook));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody BookReq book) {
        Book existingBook = bookRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No books with that id were found"));
        Author author = authorRepository
            .findById(book.getAuthor_id())
            .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors with that id were found"));
        Publisher publisher = publisherRepository
            .findById(book.getPublisher_id())
            .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publishers with that id were found"));
        existingBook.setTitle(book.getTitle());
        existingBook.setGenre(book.getGenre());
        existingBook.setAuthor(author);
        existingBook.setPublisher(publisher);
        if(book.getGenre() == null || book.getTitle() == null || author == null || publisher == null){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not update book, please check all required fields are correct");
        }
        return ResponseEntity.ok(bookRepository.save(existingBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book book = bookRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with that id were found"));
        bookRepository.delete(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
