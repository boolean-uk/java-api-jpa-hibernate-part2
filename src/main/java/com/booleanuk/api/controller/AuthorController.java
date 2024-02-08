package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Publisher;
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
    public ResponseEntity<Author> getAnAuthor(@PathVariable int id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        String nameRegex = "^/^[a-zA-Z\\s]*$/";
        String emailRegex = "^[^@]+@[^@]+\\.[^@]+$";
        if(author.getFirstName().matches(nameRegex) && author.getLastName().matches(nameRegex) && author.getEmail().matches(emailRegex)) {
            Author newAuthor = this.authorRepository.save(author);
            newAuthor.setBooks(new ArrayList<>());
            return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parameters conflicts with requirements");
        }


    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        String nameRegex = "^/^[a-zA-Z\\s]*$/";
        String emailRegex = "^[^@]+@[^@]+\\.[^@]+$";
        if(author.getFirstName().matches(nameRegex) && author.getLastName().matches(nameRegex) && author.getEmail().matches(emailRegex)) {
            authorToUpdate.setFirstName(author.getFirstName());
            authorToUpdate.setLastName(author.getLastName());
            authorToUpdate.setEmail(author.getEmail());
            authorToUpdate.setAlive(author.isAlive());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parameters conflicts with requirements");
        }
        return new ResponseEntity<>(this.authorRepository.save(authorToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        Author authorToDelete = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        if(!authorToDelete.getBooks().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author has books, and could not be deleted");
        }
        this.authorRepository.delete(authorToDelete);
        authorToDelete.setBooks(new ArrayList<>());
        return ResponseEntity.ok(authorToDelete);
    }
}
