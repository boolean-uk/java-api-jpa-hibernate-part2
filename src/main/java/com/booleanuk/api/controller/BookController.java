package com.booleanuk.api.controller;

import com.booleanuk.api.model.Book;
import com.booleanuk.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class BookController {
    @Autowired
    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> getAll(){
        return this.bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable int id){
        return this.bookRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<Book> createPublisher(@RequestBody Book publisher){
        return new ResponseEntity<Book>(this.bookRepository.save(publisher), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book){
        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        bookToUpdate.setId(book.getId());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor_id(book.getAuthor_id());
        bookToUpdate.setPublisher_id(book.getPublisher_id());

        return new ResponseEntity<Book>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id){
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.bookRepository.delete(bookToDelete);
        return  ResponseEntity.ok(bookToDelete);
    }

}
