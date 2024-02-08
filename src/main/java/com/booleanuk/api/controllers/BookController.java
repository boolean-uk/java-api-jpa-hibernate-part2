package com.booleanuk.api.controllers;

import com.booleanuk.api.model.Book;
import com.booleanuk.api.repositories.AuthorRepository;
import com.booleanuk.api.repositories.BookRepository;
import com.booleanuk.api.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    BookRepository repository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @GetMapping
    public List<Book> getAll() {
        return this.repository.findAll();
    }

    record BookDTO(String title, String genre, int author_id, int publisher_id) {}

    @PostMapping
    public Book create(@RequestBody BookDTO bookDTO) {
        Book book = new Book(bookDTO.title, bookDTO.genre);

        book.setAuthor(this.authorRepository.findById(bookDTO.author_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found")));

        book.setPublisher(this.publisherRepository.findById(bookDTO.publisher_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found")));

        return this.repository.save(book);
    }
}
