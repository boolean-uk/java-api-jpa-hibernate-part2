package com.booleanuk.api.controller;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.models.Book;
import com.booleanuk.api.models.Publisher;
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
    public ResponseEntity<Book> getOneBook(@PathVariable int id){
        Book book = this.bookRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        Publisher publisher = this.publisherRepository.findById(book.getPublisher().getId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        Author author = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        book.setPublisher(publisher);
        book.setAuthor(author);

        return new ResponseEntity<Book>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        Author author = this.authorRepository.findById(bookToUpdate.getAuthor().getId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Publisher publisher = this.publisherRepository.findById(bookToUpdate.getPublisher().getId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setPublisher(publisher);
        bookToUpdate.setAuthor(author);

        return new ResponseEntity<Book>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id){
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }













}
