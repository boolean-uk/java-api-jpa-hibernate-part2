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
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    PublisherRepository publisherRepository;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(this.bookRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        validateBookOrThrowException(book);

        Author tempAuthor = this.authorRepository
                .findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id found."));

        book.setAuthor(tempAuthor);

        Publisher tempPublisher = this.publisherRepository
                .findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id found."));

        book.setPublisher(tempPublisher);

        Book newBook = this.bookRepository.save(book);

        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = findBookOrThrowException(id);

        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        validateBookOrThrowException(book);

        Book bookToBeUpdated = findBookOrThrowException(id);

        bookToBeUpdated.setTitle(book.getTitle());
        bookToBeUpdated.setGenre(book.getGenre());

        this.bookRepository.save(bookToBeUpdated);

        return new ResponseEntity<>(bookToBeUpdated, HttpStatus.CREATED);
    }

    private Book findBookOrThrowException(int id) {
        return this.bookRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No books with that id were found."));
    }

    private void validateBookOrThrowException(Book book) {
        if(book.getTitle() == null || book.getGenre() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create book, please check all required fields are correct.");
        }
    }
}
