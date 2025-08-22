package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
import com.booleanuk.api.repository.PublisherRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
    public Book getBookById(@PathVariable int id){
        return this.bookRepository.findById(id).orElseThrow();

    }
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        // Set the Author and Publisher objects based on the provided IDs
        Author author = authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Author not found with id: " + book.getAuthor().getId()));
        Publisher publisher = publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Publisher not found with id: " + book.getPublisher().getId()));
        book.setAuthor(author);
        book.setPublisher(publisher);

        Book createdBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();

            // Set the updated fields
            existingBook.setTitle(book.getTitle());
            existingBook.setGenre(book.getGenre());

            // Set the Author and Publisher objects based on the provided IDs
            Author author = authorRepository.findById(book.getAuthor().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Author not found with id: " + book.getAuthor().getId()));
            Publisher publisher = publisherRepository.findById(book.getPublisher().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Publisher not found with id: " + book.getPublisher().getId()));
            existingBook.setAuthor(author);
            existingBook.setPublisher(publisher);

            // Update any other fields as needed
            Book updatedBook = bookRepository.save(existingBook);
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
