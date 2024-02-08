package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.repository.AuthorRepository;
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

    @GetMapping
    public List<Author> getAll() {
        return this.authorRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        if(containsNull(author)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create author, please check all required fields are correct.");
        }
        return new ResponseEntity<>(authorRepository.save(author), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable int id) {
        Author author = findAuthor(id);
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        Author authorToDelete = findAuthor(id);
        if(!authorToDelete.getBooks().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete the author because it has books attached to it.");
        }
        authorRepository.delete(authorToDelete);
        return ResponseEntity.ok(authorToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = findAuthor(id);
        if(containsNull(author)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the author, please check all required fields are correct.");
        }
        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.getAlive());

        return new ResponseEntity<>(authorRepository.save(authorToUpdate), HttpStatus.CREATED);
    }

    private Author findAuthor(int id) {
        return authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors with that id were found"));
    }

    private boolean containsNull(Author author) {
        System.out.println(author.getFirstName() + "   " + author.getLastName() +"    "+ author.getEmail() + "     "+ author.getAlive());
        return author.getFirstName() == null || author.getLastName() == null || author.getEmail() == null || author.getAlive() == null;
    }
}

