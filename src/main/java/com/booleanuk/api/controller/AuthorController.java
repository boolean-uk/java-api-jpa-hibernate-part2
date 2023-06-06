package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;
    @GetMapping()
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id){
        Author foundAuthor = authorRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Author not found"));

        return new ResponseEntity<>(foundAuthor, HttpStatus.OK);
    }
    public record AuthorRequest (String firstName,String lastName, String email,boolean alive ){}
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody AuthorRequest authorRequest){

        return new ResponseEntity<>(authorRepository.save(
                new Author ( authorRequest.firstName,
                        authorRequest.lastName,
                        authorRequest.email,
                        authorRequest.alive))
                ,HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id,@RequestBody AuthorRequest authorRequest){
        Author foundAuthor = authorRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Author not found"));

        foundAuthor.setFirstName(authorRequest.firstName);
        foundAuthor.setLastName(authorRequest.lastName);
        foundAuthor.setEmail(authorRequest.email);
        foundAuthor.setAlive(authorRequest.alive);

        return new ResponseEntity<>(authorRepository.save(foundAuthor), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Author foundAuthor = authorRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Author not found"));
        authorRepository.delete(foundAuthor);

        return new ResponseEntity<>(foundAuthor, HttpStatus.OK);
    }
}
