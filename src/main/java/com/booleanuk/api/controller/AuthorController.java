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
    public List<Author> getAllAuthors(){
        return this.authorRepository.findAll();
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable int id){
        Author author = null;
        author = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department with this ID not found"));
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        return new ResponseEntity<Author>(this.authorRepository.save(author), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
       Author authorToUpdate = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "An author with this Id can not be found"));

       authorToUpdate.setFirstName(author.getFirstName());
       authorToUpdate.setLastName(author.getLastName());
       authorToUpdate.setEmail(author.getEmail());
       authorToUpdate.setAlive(author.isAlive());

       return new ResponseEntity<Author>(this.authorRepository.save(authorToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id){
        Author authorDelete = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This Author is not in the list."));
        this.authorRepository.delete(authorDelete);
        return ResponseEntity.ok(authorDelete);
    }

}
