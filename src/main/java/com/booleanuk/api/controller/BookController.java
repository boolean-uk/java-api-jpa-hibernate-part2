package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.repository.BookRepository;
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
    public ResponseEntity<List<Book>> getAllBooks(){
        return new ResponseEntity<>(bookRepository.findAll(), HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        if(book.getTitle().isBlank() || book.getGenre().isBlank() )
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Check all required fields are correct");

        return new ResponseEntity<>(bookRepository.save(book),HttpStatus.CREATED);

    }




    @GetMapping("{id}")
    public ResponseEntity<Book> getOneBook(@PathVariable int id){
        Book book = bookRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return new ResponseEntity<>(book, HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id ,@RequestBody Book book) {

        Book bookToUpdate = bookRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(book.getTitle().isBlank() || book.getGenre().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Check all required fields are correct");

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setPublisher(book.getPublisher());

        return new ResponseEntity<>(bookRepository.save(bookToUpdate), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteAuthor(@PathVariable int id) {

        Book bookToDelete = bookRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);


    }




}
