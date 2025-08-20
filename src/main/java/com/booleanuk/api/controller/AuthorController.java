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
    public ResponseEntity<List<Author>> getAllAuthors(){
        return ResponseEntity.ok(this.authorRepository.findAll());
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author request) {
        return new ResponseEntity<Author>(this.authorRepository.save(request), HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public ResponseEntity<Author> getById(@PathVariable("id") Integer id) {
        Author author = null;
        author = this.authorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entered ID does not exist!")
        );

        return ResponseEntity.ok(author);
    }


    @PutMapping("{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entered ID does not exist!"));

        authorToUpdate.setFirst_name(author.getFirst_name());
        authorToUpdate.setLast_name(author.getLast_name());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.getAlive());

        return new ResponseEntity<Author>(this.authorRepository.save(authorToUpdate), HttpStatus.CREATED);
    }



    @DeleteMapping("{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        Author authorToDelete = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entered ID does not exist!"));
        this.authorRepository.delete(authorToDelete);
        return ResponseEntity.ok(authorToDelete);
    }
}
