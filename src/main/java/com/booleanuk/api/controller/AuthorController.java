package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;


    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> result = this.authorRepository.findAll();
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable int id) {
        return this.authorRepository.findById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No author with that id was found"
                ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author createAuthor(@Valid @RequestBody Author body) {
        return this.authorRepository.save(body);
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @Valid @RequestBody Author body) {
        return this.authorRepository.findById(id).map(author -> {
            author.setFirstName(body.getFirstName());
            author.setLastName(body.getLastName());
            author.setEmail(body.getEmail());
            author.setAlive(body.isAlive());

            this.authorRepository.save(author);
            return ResponseEntity.status(HttpStatus.CREATED).body(author);

        }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No author with that ID was found"
        ));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        return this.authorRepository.findById(id).map(author -> {

            author.getBooks().forEach(book -> {
                book.setAuthor(null);
                this.bookRepository.save(book);
            });

            this.authorRepository.delete(author);
            return ResponseEntity.ok(author);
        }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No author with that ID was found"
        ));
    }
}
