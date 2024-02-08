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
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public List<Book> getAllBooks(){
        return this.bookRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getById(@PathVariable("id") Integer id) {
        Book book = this.bookRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find "));

        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        //Regex for the strings
        String regex = "/^[a-zA-Z\\s]*$/";

        if(!book.getTitle().matches(regex) && !book.getGenre().matches(regex)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write the fields correctly");
        }
        Author author = this.authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the author"));
        book.setAuthor(author);

        Publisher publisher = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the publisher"));
        book.setPublisher(publisher);

        return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateABook(@PathVariable int id,@RequestBody Book book){
        Book bookToUpdate = this.bookRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the book...."));

        //Regex for the strings
        String regex = "/^[a-zA-Z\\s]*$/";

        if(!book.getTitle().matches(regex) && !book.getGenre().matches(regex)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write the fields correctly");
        }
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        //Retrieve the author details
        int authorId = book.getAuthor().getId();
        Author author = this.authorRepository.findById(authorId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the author"));
        bookToUpdate.setAuthor(author);

        //Retrieve the publisher details
        int publisherId = book.getPublisher().getId();
        Publisher publisher = this.publisherRepository.findById(publisherId).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the publisher"));
        bookToUpdate.setPublisher(publisher);

        return new ResponseEntity<>(this.bookRepository.save(bookToUpdate),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteABook(@PathVariable int id){
        Book bookToDelete = this.bookRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the book!!!"));
        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }
}
