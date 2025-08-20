package com.booleanuk.api.Controllers;

import com.booleanuk.api.Models.Author;
import com.booleanuk.api.Repositories.AuthorRepository;
import com.booleanuk.api.Repositories.BookRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {
    @Autowired
    AuthorRepository repo;

    @PostMapping
    public ResponseEntity<Author> add(@RequestBody Author auth){
        try{
            return new ResponseEntity<>(this.repo.save(auth), HttpStatus.CREATED);
        } catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed request");
        }
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll(){
        return ResponseEntity.ok(this.repo.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getOne(@PathVariable int id){
        Author found = this.repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with provided ID not found")
        );
        return ResponseEntity.ok(found);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> deleteOne(@PathVariable int id){
        Author toBeDeleted = this.repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Author with provided ID not found")
        );
        this.repo.delete(toBeDeleted);
        return ResponseEntity.ok(toBeDeleted);
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> putOne(@PathVariable int id, @RequestBody Author author){
        Author toBeEdited = this.repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        toBeEdited.setFirst_name(author.getFirst_name());
        toBeEdited.setLast_name(author.getLast_name());
        toBeEdited.setEmail(author.getEmail());
        toBeEdited.setAlive(author.isAlive());

        this.repo.save(toBeEdited);
        return new ResponseEntity<>(toBeEdited, HttpStatus.CREATED);
    }
}
