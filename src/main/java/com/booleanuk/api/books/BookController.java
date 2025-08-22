package com.booleanuk.api.books;

import com.booleanuk.api.authors.Author;
import com.booleanuk.api.authors.AuthorRepository;
import com.booleanuk.api.publishers.Publisher;
import com.booleanuk.api.publishers.PublisherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private PublisherRepository publisherRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getOne(@PathVariable(name="id") int id) {
        return ResponseEntity.ok(bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with that ID was found.")
        ));
    }

    @PostMapping
    public ResponseEntity<Book> createOne(@RequestBody Book book) {
        Author author = authorRepository.findById(book.getAuthorId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that ID was found.")
        );

        Publisher publisher = publisherRepository.findById(book.getPublisherId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that ID was found.")
        );

        book.setAuthor(author);
        book.setPublisher(publisher);
        return new ResponseEntity<>(bookRepository.save(book), HttpStatus.CREATED);
        //Could not create book. Please check all required fields are correct.
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable int id, @RequestBody Book book) {
        Book bookToUpdate = bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with that ID was found.")
        );

        Author author = authorRepository.findById(book.getAuthorId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that ID was found.")
        );

        Publisher publisher = publisherRepository.findById(book.getPublisherId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that ID was found.")
        );

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setAuthor(author);
        bookToUpdate.setPublisher(publisher);
        Book updatedBook = bookRepository.save(bookToUpdate);
        return new ResponseEntity<>(updatedBook, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable int id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with that ID was found.")
        );
        bookRepository.deleteById(id);
        return ResponseEntity.ok(book);
    }
}
