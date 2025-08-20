package com.booleanuk.api.Controllers;

import com.booleanuk.api.Models.Author;
import com.booleanuk.api.Models.Publisher;
import com.booleanuk.api.Repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("publishers")
public class PublisherController {
    @Autowired
    PublisherRepository repo;

    @PostMapping
    public ResponseEntity<Publisher> add(@RequestBody Publisher publisher){
        try{
            return new ResponseEntity<>(this.repo.save(publisher), HttpStatus.CREATED);
        } catch(DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed request");
        }
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> getAll(){
        return ResponseEntity.ok(this.repo.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getOne(@PathVariable int id){
        Publisher found = this.repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher with provided ID not found")
        );
        return ResponseEntity.ok(found);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> deleteOne(@PathVariable int id){
        Publisher toBeDeleted = this.repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher with provided ID not found")
        );
        this.repo.delete(toBeDeleted);
        return ResponseEntity.ok(toBeDeleted);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> putOne(@PathVariable int id, @RequestBody Publisher publisher){
        Publisher toBeEdited = this.repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        toBeEdited.setName(publisher.getName());
        toBeEdited.setLocation(publisher.getLocation());

        this.repo.save(toBeEdited);
        return new ResponseEntity<>(toBeEdited, HttpStatus.CREATED);
    }

}
