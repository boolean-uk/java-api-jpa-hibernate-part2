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
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Author author = authorRepository.findById(book.getAuthor_id()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Publisher publisher = publisherRepository.findById(book.getAuthor_id()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        book.setAuthor(author);
        book.setPublisher(publisher);
        return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id){
        Book bookToFind = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find Book with the provided id"));
        return new ResponseEntity<>(bookToFind, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book){
        Author author = authorRepository.findById(book.getAuthor_id()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        Publisher publisher = publisherRepository.findById(book.getAuthor_id()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the Book to update"));
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setAuthor(author);
        bookToUpdate.setPublisher(publisher);
        return new ResponseEntity<>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id){
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the Book to delete"));
        this.bookRepository.delete(bookToDelete);
        return new ResponseEntity<>(bookToDelete, HttpStatus.OK);
    }
}
