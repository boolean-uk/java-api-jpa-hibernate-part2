package com.booleanuk.api.authors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.ok(authorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getOne(@PathVariable(name="id") int id) {
        return ResponseEntity.ok(authorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that ID was found.")
        ));
    }

    @PostMapping
    public ResponseEntity<Author> createOne(@RequestBody Author author) {
        return new ResponseEntity<>(authorRepository.save(author), HttpStatus.CREATED);
        //Could not create author. Please check all required fields are correct.
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = authorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that ID was found.")
        );
        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());
        Author updatedAuthor = authorRepository.save(authorToUpdate);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> delete(@PathVariable int id) {
        Author author = authorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that ID was found.")
        );
        authorRepository.deleteById(id);
        return ResponseEntity.ok(author);
    }
}
