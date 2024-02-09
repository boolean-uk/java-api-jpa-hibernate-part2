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

//    public BookController(BookRepository bookRepository) {
//        this.bookRepository = bookRepository;
//    }

    @GetMapping
    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getById(@PathVariable int id) {
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        return ResponseEntity.ok(book);
    }

//    record PostBook(String title, String genre) {}

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Author authorToUpdate = this.authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author Not Found"));
        Publisher publisherToUpdate = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher Not Found"));

        book.setAuthor(authorToUpdate);
        book.setPublisher(publisherToUpdate);

        return ResponseEntity.ok(this.bookRepository.save(book));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book bookToUpdate = this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());

        Author authorToUpdate = this.authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        Publisher publisherToUpdate = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        book.setAuthor(authorToUpdate);
        book.setPublisher(publisherToUpdate);

        return new ResponseEntity<Book>(bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete = this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.bookRepository.delete(bookToDelete);

        return ResponseEntity.ok(bookToDelete);
    }
}
