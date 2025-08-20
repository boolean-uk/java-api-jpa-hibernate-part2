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


    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author){
        if(author.getFirstName().isBlank() || author.getLastName().isBlank() || author.getEmail().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Check all required fields are correct");

        return new ResponseEntity<>(authorRepository.save(author),HttpStatus.CREATED);

    }


    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors(){
        return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<Author> getOneAuthor(@PathVariable int id){
        Author author = authorRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return new ResponseEntity<>(author, HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id ,@RequestBody Author author) {

        Author authorToUpdate = authorRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(author.getFirstName().isBlank() || author.getLastName().isBlank() || author.getEmail().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Check all required fields are correct");

        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());

        return new ResponseEntity<>(authorRepository.save(authorToUpdate), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {

        Author authorToDelete = authorRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        authorRepository.delete(authorToDelete);

        return new ResponseEntity<>(authorToDelete, HttpStatus.OK);


    }

}
