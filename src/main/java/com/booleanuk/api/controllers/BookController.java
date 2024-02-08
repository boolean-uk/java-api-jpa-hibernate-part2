package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Book;
import com.booleanuk.api.repositories.BookRepository;
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

    @GetMapping
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getOneBook(@PathVariable int id)    {
        return new ResponseEntity<>(this.bookRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No books with that id were found")
                ), HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Book> createOneBook(@RequestBody Book book)   {
        if(book.getTitle() == null
        || book.getGenre() == null)
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No books with that id were found");

        return new ResponseEntity<>(
                this.bookRepository.save(book),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> updateOneBook(@PathVariable int id, @RequestBody Book book) {
        if(book.getTitle() == null
                || book.getGenre() == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not update the book, please check all required fields are correct");

        Book bookToUpdate = this.bookRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No books with that id were found"
                        )
                );

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setPublisher(book.getPublisher());

        return new ResponseEntity<>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteOneBook(@PathVariable int id) {
        Book bookToDelete = this.bookRepository.findById(id)
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "No books with that id were found")
                );

        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }
}
