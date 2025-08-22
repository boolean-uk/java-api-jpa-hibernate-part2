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

@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    private BookRepository books;

    @Autowired
    private AuthorRepository authors;

    @Autowired
    private PublisherRepository publishers;

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book toAdd) {
        Publisher publisher = this.publishers.findById(toAdd.getPublisher().getId())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Given publisher not found.")
                );

        Author author = this.authors.findById(toAdd.getAuthor().getId())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Given author not found.")
                );

        toAdd.setAuthor(author);
        toAdd.setPublisher(publisher);

        return new ResponseEntity<>(this.books.save(toAdd), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return new ResponseEntity<>(this.books.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getOne(@PathVariable int id) {
        Book toReturn = this.books.findById(id).
                orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
        return new ResponseEntity<>(toReturn, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> delete(@PathVariable int id) {
        Book toDelete = this.books.findById(id).
                orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
        this.books.delete(toDelete);
        return new ResponseEntity<>(toDelete, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> update(@PathVariable int id, @RequestBody Book newData) {
        Book toUpdate = this.books.findById(id).
                orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );

        Publisher publisher = this.publishers.findById(newData.getPublisher().getId()).
                orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );

        Author author = this.authors.findById(newData.getAuthor().getId()).
                orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );


        toUpdate.setPublisher(publisher);
        toUpdate.setAuthor(author);
        toUpdate.setGenre(newData.getGenre());
        toUpdate.setTitle(newData.getTitle());

        return new ResponseEntity<>(toUpdate, HttpStatus.CREATED);
    }

}
