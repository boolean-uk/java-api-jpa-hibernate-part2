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
    public List<Book> getAll(){
        return this.bookRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book){
        //if you have many to one relation in employee 'class for department, then
        //you need to do it like this, by making a temp department.
        Author tempAuthor = authorRepository.findById(book.getAuthor()
                        .getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No author with ID"));
        book.setAuthor(tempAuthor);

        Publisher tempPublisher = publisherRepository.findById(book.getPublisher()
                        .getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No publisher with ID"));
        book.setPublisher(tempPublisher);

        return ResponseEntity.ok(bookRepository.save(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable int id){
        Book delete = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.bookRepository.delete(delete);
        return ResponseEntity.ok(delete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateEmployee(@PathVariable int id, @RequestBody Book book){
        Book update = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        Author tempAuthor = authorRepository.findById(book.getAuthor()
                        .getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No author with ID"));
        update.setAuthor(tempAuthor);

        Publisher tempPublisher = publisherRepository.findById(book.getPublisher()
                        .getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No publisher with ID"));
        update.setPublisher(tempPublisher);
        update.setTitle(book.getTitle());
        update.setGenre(book.getGenre());
        return new ResponseEntity<>(this.bookRepository.save(update), HttpStatus.CREATED);
    }
}
