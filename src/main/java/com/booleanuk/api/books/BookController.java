package com.booleanuk.api.books;

import com.booleanuk.api.authors.Author;
import com.booleanuk.api.authors.AuthorRepository;
import com.booleanuk.api.publishers.Publisher;
import com.booleanuk.api.publishers.PublisherRepository;
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
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(this.bookRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Book> createNewBook(@RequestBody Book book) {
        Publisher publisher = this.publisherRepository.findById(book.getPublisher().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "no publisher with that id was found")
        );
        Author author = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "no author with that id was found")
        );
        book.setPublisher(publisher);
        book.setAuthor(author);
        return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with that id found")
        );
        return ResponseEntity.ok(book);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with that id was found")
        );
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setPublisher(book.getPublisher());
        return new ResponseEntity<Book>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with that id was found")
        );
        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }

}