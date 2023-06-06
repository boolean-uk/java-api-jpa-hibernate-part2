package com.booleanuk.api.controllers;

import com.booleanuk.api.models.*;
import com.booleanuk.api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    private record BookRequestBodyDTO (String title, String genre, int author_id, int publisher_id) {}
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
        Author author = this.authorRepo.findById(bookDTO.author_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with this id doesn't exist."));

        Publisher publisher = this.publisherRepo.findById(bookDTO.publisher_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher with this id doesn't exist."));

        Book newBook = this.bookRepo.save(new Book(bookDTO.title(), bookDTO.genre(), author, publisher));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(newBook.getId()).toUri();

        return ResponseEntity.created(location).body(newBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable int id, @RequestBody BookRequestBodyDTO bookDTO) {
        Author author = this.authorRepo.findById(bookDTO.author_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with this id doesn't exist."));

        Publisher publisher = this.publisherRepo.findById(bookDTO.publisher_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher with this id doesn't exist."));

        Book bookToUpdate = this.bookRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with this id doesn't exist."));

        bookToUpdate.setTitle(bookDTO.title());
        bookToUpdate.setGenre(bookDTO.genre());
        bookToUpdate.setAuthor(author);
        bookToUpdate.setPublisher(publisher);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        return ResponseEntity.created(location).body(this.bookRepo.save(bookToUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable int id) {
        Book bookToDelete = this.bookRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with this id doesn't exist."));

        this.bookRepo.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }
}
