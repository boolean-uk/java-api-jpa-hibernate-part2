package com.booleanuk.api.controllers;

import com.booleanuk.api.modules.Author;
import com.booleanuk.api.modules.Book;
import com.booleanuk.api.modules.Publisher;
import com.booleanuk.api.repositories.AuthorRepository;
import com.booleanuk.api.repositories.BookRepository;
import com.booleanuk.api.repositories.PublisherRepository;
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

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id){
        Book book = findOneBook(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Author author = this.authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Publisher publisher = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        book.setAuthor(author);
        book.setPublisher(publisher);

        return new ResponseEntity<Book>(this.bookRepository.save(book),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book){
        Book updateBook = findOneBook(id);

        Author tempAuthor = this.authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Publisher tempPublisher = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        updateBook.setTitle(book.getTitle());
        updateBook.setGenre(book.getGenre());
        updateBook.setAuthor(tempAuthor);
        updateBook.setPublisher(tempPublisher);

        return new ResponseEntity<Book>(this.bookRepository.save(updateBook),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id){
        Book deleteBook = findOneBook(id);
        this.bookRepository.delete(deleteBook);
        return ResponseEntity.ok(deleteBook);
    }

    private Book findOneBook(int id){
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
