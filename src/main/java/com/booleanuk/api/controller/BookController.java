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
import java.util.Optional;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    BookRepository rep;

    @Autowired
    AuthorRepository author_rep;

    @Autowired
    PublisherRepository publisher_rep;
    
    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(rep.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getById(@PathVariable Integer id) {
        Book book = this.rep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find Book by ID"));
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Author author = author_rep.findById(book.getAuthor().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find author with ID"));
        Publisher publisher = publisher_rep.findById(book.getPublisher().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find publisher with ID"));
        book.setAuthor(author);
        book.setPublisher(publisher);
        return new ResponseEntity<>(this.rep.save(book), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> delete(@PathVariable Integer id) {
        Book deleteBook = this.rep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find Book by ID"));
        this.rep.delete(deleteBook);
        return ResponseEntity.ok(deleteBook);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> update(@PathVariable Integer id, @RequestBody Book book){
        Book oldBook = getById(id).getBody();

        Author author = author_rep.findById(oldBook.getAuthor().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find author with ID"));
        Publisher publisher = publisher_rep.findById(oldBook.getPublisher().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find publisher with ID"));

        oldBook.setAuthor(author);
        oldBook.setPublisher(publisher);
        oldBook.setTitle(book.getTitle());
        oldBook.setGenre(book.getGenre());

        return new ResponseEntity<>(this.rep.save(oldBook), HttpStatus.CREATED);
    }
}
