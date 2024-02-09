package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Book;
import com.booleanuk.api.repositories.AuthorRepo;
import com.booleanuk.api.repositories.BookRepo;
import com.booleanuk.api.repositories.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {
    record PostBook(String title, String genre, Integer author_id, Integer publisher_id) {}

    @Autowired
    private BookRepo repository;
    @Autowired
    private AuthorRepo authorRepository;
    @Autowired
    private PublisherRepo publisherRepository;

    @GetMapping
    public List<Book> getAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getById(@PathVariable final Integer id) {
        return new ResponseEntity<>(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody PostBook request) {
        if (request.publisher_id == null || request.author_id == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID status: " + request.publisher_id + " - " + request.author_id);
        return new ResponseEntity<>(repository.save(new Book(request.title, request.genre, authorRepository.findById(request.author_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)), publisherRepository.findById(request.publisher_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)))), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> update(@PathVariable final Integer id, @RequestBody final Book book) {
        Book _targetBook = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found."));

        _targetBook.setTitle(book.getTitle());
        _targetBook.setGenre(book.getGenre());
        _targetBook.setAuthor_id(book.getAuthor_id());
        _targetBook.setPublisher_id(book.getPublisher_id());
        
        return new ResponseEntity<>(repository.save(_targetBook), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> remove(@PathVariable final Integer id) {
        return new ResponseEntity<>(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")), HttpStatus.OK);
    }
}
