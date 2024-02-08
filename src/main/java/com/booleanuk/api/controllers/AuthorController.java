package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Author;
import com.booleanuk.api.repositories.AuthorRepository;
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
    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getOneAuthor(@PathVariable int id)    {
        return new ResponseEntity<>(
                this.authorRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No authors with that id were found")
                ), HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Author> createOneAuthor(@RequestBody Author author)   {
        if(author.getFirstName() == null
        || author.getLastName()  == null
        || author.getEmail()     == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not create author, please check all required fields are correct"
            );

        return new ResponseEntity<>(this.authorRepository.save(author), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> updateOneAuthor(@PathVariable int id, @RequestBody Author author) {
        if(author.getFirstName() == null
                || author.getLastName()  == null
                || author.getEmail()     == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Could not update author, please check all required fields are correct"
            );
        Author authorToUpdate = this.authorRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No authors with that id were found")
                );

        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(authorToUpdate.getEmail());
        authorToUpdate.setAlive(author.isAlive());

        return new ResponseEntity<>(this.authorRepository.save(authorToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> deleteOneAuthor(@PathVariable int id) {
        Author authorToDelete = this.authorRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No authors with that id were found"
                        )
                );
        this.authorRepository.delete(authorToDelete);
        return ResponseEntity.ok(authorToDelete);
    }
}
