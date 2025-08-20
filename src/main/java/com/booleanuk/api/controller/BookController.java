package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
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
    public ResponseEntity<Book> createBook(@RequestBody Map<String, Object> bookMap) {
        Long authorId = Long.valueOf(bookMap.get("author_id").toString());
        Long publisherId = Long.valueOf(bookMap.get("publisher_id").toString());

        Author author = authorRepository.findById(authorId).orElseThrow();
        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow();

        Book book = new Book();
        book.setTitle((String) bookMap.get("title"));
        book.setGenre((String) bookMap.get("genre"));
        book.setAuthor(author);
        book.setPublisher(publisher);

        Book saved = bookRepository.save(book);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Map<String, Object> bookMap) {
        return bookRepository.findById(id).map(existing -> {
            Long authorId = Long.valueOf(bookMap.get("author_id").toString());
            Long publisherId = Long.valueOf(bookMap.get("publisher_id").toString());

            Author author = authorRepository.findById(authorId).orElseThrow();
            Publisher publisher = publisherRepository.findById(publisherId).orElseThrow();

            existing.setTitle((String) bookMap.get("title"));
            existing.setGenre((String) bookMap.get("genre"));
            existing.setAuthor(author);
            existing.setPublisher(publisher);

            bookRepository.save(existing);
            return ResponseEntity.status(201).body(existing);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        return bookRepository.findById(id).map(existing -> {
            bookRepository.delete(existing);
            return ResponseEntity.ok().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}


