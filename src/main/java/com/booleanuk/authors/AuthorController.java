package com.booleanuk.authors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("authors")
public class AuthorController {
    @Autowired
    private AuthorRepository repo;

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok(this.repo.findAll());
    }

    @PostMapping
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        return new ResponseEntity<Author>(this.repo.save(author), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getOneAuthor(@PathVariable int id) {
        return ResponseEntity.ok(this.repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find author with this id")));
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
        Author authorToUpdate = this.repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find author with this id"));
        authorToUpdate.setFirst_name(author.getFirst_name());
        authorToUpdate.setLast_name(author.getLast_name());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());

        return new ResponseEntity<Author>(this.repo.save(authorToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        Author authorToDelete = this.repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find author with this id"));

        this.repo.delete(authorToDelete);
        return ResponseEntity.ok(authorToDelete);
    }
}
