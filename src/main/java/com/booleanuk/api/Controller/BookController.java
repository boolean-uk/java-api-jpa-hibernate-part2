package com.booleanuk.api.Controller;

import com.booleanuk.api.Model.Author;
import com.booleanuk.api.Model.Publisher;
import com.booleanuk.api.Repository.AuthorRepository;
import com.booleanuk.api.Repository.BookRepository;
import com.booleanuk.api.Model.Book;
import com.booleanuk.api.Repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("books")
class BookController {

    @Autowired
    private BookRepository bookRepository;

    private AuthorRepository authorRepository;
    private PublisherRepository publisherRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @GetMapping
    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable int id) {
        Book book = this.bookRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(book);
    }

    record BookRequest(String title, String genre, Integer author_id, Integer publisher_id) {}

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody BookRequest request) {
        validate(request);
        Author author = this.authorRepository.findById(request.author_id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        Publisher publisher = this.publisherRepository.findById(request.publisher_id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        Book book = new Book(request.title, request.genre, author, publisher);
        return new ResponseEntity<Book>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable int id, @RequestBody BookRequest request) {
        validate(request);
        Book book = this.bookRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        Author author = this.authorRepository.findById(request.author_id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        Publisher publisher = this.publisherRepository.findById(request.publisher_id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        book.setTitle(request.title());
        book.setGenre(request.genre());
        book.setAuthor(author);
        book.setPublisher(publisher);
        return new ResponseEntity<Book>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable int id) {
        Book book = this.bookRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        this.bookRepository.delete(book);
        return ResponseEntity.ok(book);
    }

    public void validate(BookRequest book) {
        if (book.title() == null || book.genre() == null || book.author_id() == null || book.publisher_id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not create/update book, please check all required fields are correct.");
        }
    }
}
