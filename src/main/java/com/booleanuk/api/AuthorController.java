package com.booleanuk.api;

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

    @GetMapping("{id}")
    public Author getById(@PathVariable("id") Integer id) {
        return this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author) {
        try {
            return new ResponseEntity<>(this.authorRepository.save(author), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateOneAuthor(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = this.authorRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (author.getFirstName() == null || author.getLastName() == null || author.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());

        return new ResponseEntity<>(this.authorRepository.save(authorToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteOneAuthor(@PathVariable int id){
        Author authorToDelete = this.authorRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.authorRepository.delete(authorToDelete);
        return new ResponseEntity<>(authorToDelete, HttpStatus.ACCEPTED);
    }
}