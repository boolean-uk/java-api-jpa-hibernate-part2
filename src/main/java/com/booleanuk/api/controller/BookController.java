package com.booleanuk.api.controller;

import com.booleanuk.api.dto.BookDTO;
import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
import com.booleanuk.api.repository.PublisherRepository;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> result = this.bookRepository.findAll();
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        return this.bookRepository.findById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No book with that id was found"
                ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody BookDTO body) {

        Author author = authorRepository.findById(body.getAuthorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Author not found")
                );

        Publisher publisher = publisherRepository.findById(body.getPublisherId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Publisher not found")
                );

        Book book = new Book();
        book.setTitle(body.getTitle());
        book.setGenre(body.getGenre());
        book.setAuthor(author);
        book.setPublisher(publisher);
        return this.bookRepository.save(book);
    }



    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @Valid @RequestBody Book body) {
        return this.bookRepository.findById(id).map(book -> {
            book.setTitle(body.getTitle());
            book.setGenre(body.getGenre());
            book.setAuthor(body.getAuthor());
            book.setPublisher(body.getPublisher());

            this.bookRepository.save(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(book);

        }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No book with that ID was found"
        ));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        return this.bookRepository.findById(id).map(book -> {
            this.bookRepository.delete(book);
            return ResponseEntity.ok(book);
        }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No book with that ID was found"
        ));
    }
}
