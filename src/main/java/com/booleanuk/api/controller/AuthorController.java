package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Author> getAllAuthors(){
        return this.authorRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getById(@PathVariable("id") Integer id) {
        Author author = this.authorRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find "));

        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author) {
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
        Author createdAuthor = this.authorRepository.save(author);
        createdAuthor.setBooks(new ArrayList<>());
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAnAuthor(@PathVariable int id,@RequestBody Author author){
        Author authorToUpdate = this.authorRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the author...."));

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
        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());

        return new ResponseEntity<>(this.authorRepository.save(authorToUpdate),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAnAuthor(@PathVariable int id){
        Author authorToDelete = this.authorRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the author!!!"));
        this.authorRepository.delete(authorToDelete);
        authorToDelete.setBooks(new ArrayList<>());
        return ResponseEntity.ok(authorToDelete);
    }
}
