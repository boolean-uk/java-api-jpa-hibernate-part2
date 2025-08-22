package com.booleanuk.api.controller;
import com.booleanuk.api.model.Author;
import com.booleanuk.api.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Author author) {
        try {
            Author savedAuthor = this.authorRepository.save(author);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Could not create author, please check all required fields are correct.");
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Author> getAll() {
        return this.authorRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        if (this.authorRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(this.authorRepository.findById(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No authors with that id were found");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable int id, @RequestBody Author authors) {
        Author existingauthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors with that id found"));
        if (authors.getFirst_name() == null || authors.getLast_name() == null || authors.getEmail() == null || authors.getAlive() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not update the author, please check all required fields are correct.");
        }

        existingauthor.setFirst_name(authors.getFirst_name());
        existingauthor.setLast_name(authors.getLast_name());
        existingauthor.setEmail(authors.getEmail());
        existingauthor.setAlive(authors.getAlive());
        Author updatedAuthor = authorRepository.save(existingauthor);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable int id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors with that id found"));

        this.authorRepository.delete(author);
        return ResponseEntity.ok(author);

    }








}