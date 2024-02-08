package com.booleanuk.api.authors;

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
    private AuthorRepository authors;

    @GetMapping
    public List<Author> getAll(){
        return authors.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getOne(@PathVariable int id){
        return ResponseEntity.ok(getByID(id));
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author){
        if (authors.existsByFirstNameAndLastName(author.getFirstName(), author.getLastName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This author is already registered.");
        }
        return new ResponseEntity<Author>(authors.save(author), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Author> update(@PathVariable int id, @RequestBody Author author){
        Author toUpdate = getByID(id);

        if (authors.existsByFirstNameAndLastName(author.getFirstName(), author.getLastName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This author is already registered.");
        }

        toUpdate.setFirstName(author.getFirstName());
        toUpdate.setLastName(author.getLastName());
        toUpdate.setEmail(author.getEmail());
        toUpdate.setAlive(author.isAlive());

        return new ResponseEntity<Author>(authors.save(toUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Author> delete(@PathVariable int id){
        Author toDelete = getByID(id);
        authors.delete(toDelete);

        return ResponseEntity.ok(toDelete);
    }

    private Author getByID(int id){
        return authors
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
