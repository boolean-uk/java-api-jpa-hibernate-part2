package com.booleanuk.api.author;

import com.booleanuk.api.publisher.Publisher;
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
    public ResponseEntity<Author> getAuthor(@PathVariable int id) {
        Author author = this.authorRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));

        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        //Regex to make sure the names are strings
        String regexName = "/^[a-zA-Z\\s]*$/";

        if(!author.getFirstName().matches(regexName) && !author.getLastName().matches(regexName)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write the names correctly");
        }

        //Regex for email
        String regexEmail = "^(.+)@(\\S+)$";
        if(!author.getEmail().matches(regexEmail)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write a valid email!");
        }

        return new ResponseEntity<Author>(this.authorRepository.save(author), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor (@PathVariable int id) {
        Author deleted = this.authorRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        this.authorRepository.delete(deleted);
        return ResponseEntity.ok(deleted);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor (@PathVariable int id, @RequestBody Author author) {
        //Regex to make sure the names are strings
        String regexName = "/^[a-zA-Z\\s]*$/";

        if(!author.getFirstName().matches(regexName) && !author.getLastName().matches(regexName)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write the names correctly");
        }

        //Regex for email
        String regexEmail = "^(.+)@(\\S+)$";
        if(!author.getEmail().matches(regexEmail)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write a valid email!");
        }
        Author publisherToUpdate = this.authorRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        publisherToUpdate.setFirstName(author.getFirstName());
        publisherToUpdate.setLastName(author.getLastName());
        publisherToUpdate.setEmail(author.getEmail());
        publisherToUpdate.setAlive(author.isAlive());
        return new ResponseEntity<Author>(this.authorRepository.save(publisherToUpdate), HttpStatus.CREATED);
    }
}
