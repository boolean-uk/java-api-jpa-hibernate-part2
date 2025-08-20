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
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(this.bookRepository.findAll());
    }



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book request) {
        Author author = null;
        author = this.authorRepository.findById(request.getAuthor().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id exists")
        );
        request.setAuthor(author);



        Publisher publisher = null;
        publisher = this.publisherRepository.findById(request.getPublisher().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id exists")
        );
        request.setPublisher(publisher);

        return new ResponseEntity<Book>(this.bookRepository.save(request), HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public ResponseEntity<Book> getById(@PathVariable("id") Integer id) {
        Book book = null;
        book = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entered ID does not exist!")
        );

        return ResponseEntity.ok(book);
    }


    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entered ID does not exist!"));

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());


        Author author = null;
        author = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id exists")
        );
        bookToUpdate.setAuthor(author);



        Publisher publisher = null;
        publisher = this.publisherRepository.findById(book.getPublisher().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id exists")
        );
        bookToUpdate.setPublisher(publisher);

        return new ResponseEntity<Book>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }



    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entered ID does not exist!"));
        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }
}
