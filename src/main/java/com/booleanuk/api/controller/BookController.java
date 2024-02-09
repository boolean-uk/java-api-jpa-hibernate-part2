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
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    record BookDTO(String title, String genre, int author_id, int publisher_id){}
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
       return this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO) {
        if (bookDTO.title == null || bookDTO.genre == null || bookDTO.author_id < 1 || bookDTO.publisher_id < 1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        Book book = new Book(bookDTO.title, bookDTO.genre);

        book.setAuthor(this.authorRepository.findById(bookDTO.author_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found")));

        book.setAuthor(this.authorRepository.findById(bookDTO.publisher_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found")));

        return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO) {
        if (bookDTO.title == null || bookDTO.genre == null || bookDTO.author_id < 1 || bookDTO.publisher_id < 1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        Book book = this.getBookById(id);
        book.setTitle(bookDTO.title);
        book.setGenre(bookDTO.genre);

        book.setAuthor(this.authorRepository.findById(bookDTO.author_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found")));

        book.setAuthor(this.authorRepository.findById(bookDTO.publisher_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found")));

        return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public Book deleteBook(@PathVariable int id) {
        Book bookToDelete = this.getBookById(id);
        this.bookRepository.delete(bookToDelete);
        return bookToDelete;
    }


}