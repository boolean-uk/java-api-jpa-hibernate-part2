package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.models.Book;
import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.models.dtos.BookRequestBodyDTO;
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
    private BookRepository bookRepo;
    @Autowired
    private AuthorRepository authorRepo;
    @Autowired
    private PublisherRepository publisherRepo;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(this.bookRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable int id) {
        return ResponseEntity.ok(this.bookRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with this id doesn't exist.")));
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody BookRequestBodyDTO bookDTO) {
        Author author = this.authorRepo.findById(bookDTO.getAuthor_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with this id doesn't exist."));

        Publisher publisher = this.publisherRepo.findById(bookDTO.getPublisher_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher with this id doesn't exist."));

        Book newBook = new Book(bookDTO.getTitle(), bookDTO.getGenre());
        newBook.setAuthor(author);
        newBook.setPublisher(publisher);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.bookRepo.save(newBook));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable int id, @RequestBody BookRequestBodyDTO bookDTO) {
        Author author = this.authorRepo.findById(bookDTO.getAuthor_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with this id doesn't exist."));

        Publisher publisher = this.publisherRepo.findById(bookDTO.getPublisher_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher with this id doesn't exist."));

        Book bookToUpdate = this.bookRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with this id doesn't exist."));

        bookToUpdate.setTitle(bookDTO.getTitle());
        bookToUpdate.setGenre(bookDTO.getGenre());
        bookToUpdate.setAuthor(author);
        bookToUpdate.setPublisher(publisher);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.bookRepo.save(bookToUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable int id) {
        Book bookToDelete = this.bookRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with this id doesn't exist."));

        this.bookRepo.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }
}
