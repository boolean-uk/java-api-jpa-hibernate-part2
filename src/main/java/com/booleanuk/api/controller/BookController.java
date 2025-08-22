package com.booleanuk.api.controller;


import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.BookDTO;
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
    private final BookRepository bookRepository;
    @Autowired
    private final AuthorRepository authorRepository;
    @Autowired
    private final PublisherRepository publisherRepository;

    @Autowired
    public BookController (BookRepository repository, AuthorRepository authorRepository, PublisherRepository publisherRepository){
        this.bookRepository = repository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return this.bookRepository.findAll();
    }


    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO) {
        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Publisher publisher = publisherRepository.findById(bookDTO.getPublisherId())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));


        Book book = new Book();

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setGenre(bookDTO.getGenre());

        Book savedBook = bookRepository.save(book);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id){
        Book book = null;
        book = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Book with that ID found")
        );
        return ResponseEntity.ok(book);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book){
        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No Book with that ID found")
        );
        book.setId(bookToUpdate.getId());
        return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id){
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No book with that ID found")
        );
        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }


}