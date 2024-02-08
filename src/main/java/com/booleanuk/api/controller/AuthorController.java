package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable int id) {
        Author author = this.getAuthor(id);
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        this.checkHasRequiredValues(author);
        Author createdAuthor = this.authorRepository.save(author);
        createdAuthor.setBooks(new ArrayList<Book>());
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        Author authorToDelete = this.getAuthor(id);
        this.authorRepository.delete(authorToDelete);
        authorToDelete.setBooks(new ArrayList<Book>());
        return ResponseEntity.ok(authorToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
        this.checkHasRequiredValues(author);
        Author authorToUpdate = this.getAuthor(id);
        authorToUpdate.setFirst_name(author.getFirst_name());
        authorToUpdate.setLast_name(author.getLast_name());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());
        authorToUpdate.setBooks(new ArrayList<Book>());
        return new ResponseEntity<>(this.authorRepository.save(authorToUpdate), HttpStatus.CREATED);
    }

    private Author getAuthor(int id) {
        return this.authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors with that id were found"));
    }

    private void checkHasRequiredValues(Author author) {
        if (author.getFirst_name() == null || author.getLast_name() == null || author.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please check all required fields are correct.");
        }
    }
}
