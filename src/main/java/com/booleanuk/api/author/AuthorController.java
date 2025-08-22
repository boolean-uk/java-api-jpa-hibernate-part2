package com.booleanuk.api.author;

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

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        if (author == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.authorRepository.save(author), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable int id) {
        return new ResponseEntity<>(this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No authors with that id were found")), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
        if (author == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the author, please check all required fields are correct");
        }
        Author updateAuthor = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No author with that id were found "));
        updateAuthor.setFirst_name(author.getFirst_name());
        updateAuthor.setLast_name(author.getLast_name());
        updateAuthor.setEmail(author.getEmail());
        updateAuthor.setAlive(author.isAlive());


        return new ResponseEntity<>(this.authorRepository.save(updateAuthor), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        Author deletedAuthor = this.authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id were found"));
        this.authorRepository.delete(deletedAuthor);
        return ResponseEntity.ok(deletedAuthor);
    }

}
