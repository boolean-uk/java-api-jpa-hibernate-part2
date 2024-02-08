package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.models.Book;
import com.booleanuk.api.models.BookRequest;
import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.repositories.AuthorRepository;
import com.booleanuk.api.repositories.BookRepository;
import com.booleanuk.api.repositories.PublisherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("books")
public class BookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book add(@RequestBody BookRequest request) {
        // Check if request is a valid book
        checkIfValidObject(request);
        // Get author and publisher from ids
        Author author = this.authorRepository.findById(request.getAuthorId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find Author[id=" + request.getAuthorId() + "]"));
        Publisher publisher = this.publisherRepository.findById(request.getPublisherId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find Publisher[id=" + request.getPublisherId() + "]"));
        // Create new book object
        Book bookToAdd = new Book(request.getTitle(), request.getGenre(), author, publisher);
        // Add object to db
        return this.bookRepository.save(bookToAdd);
    }

    @GetMapping
    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    @GetMapping("{id}")
    public Book getById(@PathVariable int id) {
        return this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Book updateById(@PathVariable int id, @RequestBody BookRequest request) {
        checkIfValidObject(request);

        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Author author = this.authorRepository.findById(request.getAuthorId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find Author[id=" + request.getAuthorId() + "]"));
        Publisher publisher = this.publisherRepository.findById(request.getPublisherId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find Publisher[id=" + request.getPublisherId() + "]"));

        bookToUpdate.setTitle(request.getTitle());
        bookToUpdate.setGenre(request.getGenre());
        bookToUpdate.setAuthor(author);
        bookToUpdate.setPublisher(publisher);

        return this.bookRepository.save(bookToUpdate);
    }

    @DeleteMapping("{id}")
    public Book deleteById(@PathVariable int id) {
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.bookRepository.delete(bookToDelete);
        return bookToDelete;
    }

    private void checkIfValidObject(BookRequest book) {
        // log.info(book.toString());
        if (Stream.of(book.getTitle(), book.getGenre(), book.getAuthorId(), book.getPublisherId())
                .anyMatch(Objects::isNull)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create object. Required fields are NULL.");
        }
        if (Stream.of(book.getTitle(), book.getGenre())
                .anyMatch(String::isBlank)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create object. Required fields are empty.");
        }
    }
}
