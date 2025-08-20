package com.booleanuk.api.controllers;


import com.booleanuk.api.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.booleanuk.api.repositories.BookRepository;
import com.booleanuk.api.models.Book;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    BookRepository repo;

    @GetMapping
    public ResponseEntity<List<Book>> getAll(){
        return ResponseEntity.ok(this.repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable int id){
        Book book = this.repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This is not found by dave!"));
        return ResponseEntity.ok(book);
    }
    @PostMapping
    public ResponseEntity<Book> add(@RequestBody Book book){

        return new ResponseEntity<>(this.repo.save(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update( @PathVariable int id,@RequestBody Book book){
        Book empToUpdate = this.repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This is not found by dave!"));
        empToUpdate.setTitle(book.getTitle());
        empToUpdate.setGenre(book.getGenre());
        empToUpdate.setAuthor(book.getAuthor());
        empToUpdate.setPublisher(book.getPublisher());

        return new ResponseEntity<>(this.repo.save(empToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable int id){
        Book empToBeDeleted = this.repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This is not found by dave!"));
        this.repo.delete(empToBeDeleted);
        return ResponseEntity.ok(empToBeDeleted);
    }

}
