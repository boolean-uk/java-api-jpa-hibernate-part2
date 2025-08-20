package com.booleanuk.api.controllers;


import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.models.Publisher;
import com.booleanuk.api.repositories.PublisherRepository;
import com.booleanuk.api.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @GetMapping
    public ResponseEntity<List<Publisher>> getAll(){
        return ResponseEntity.ok(this.repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> get(@PathVariable int id){
        Publisher publisher = this.repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This is not found by dave!"));
        return ResponseEntity.ok(publisher);
    }
    @PostMapping
    public ResponseEntity<Publisher> add(@RequestBody Publisher publisher){
        return new ResponseEntity<>(this.repo.save(publisher), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> update( @PathVariable int id,@RequestBody Publisher publisher){
        Publisher empToUpdate = this.repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This is not found by dave!"));
        empToUpdate.setLocation(publisher.getLocation());
        empToUpdate.setName(publisher.getName());

        return new ResponseEntity<>(this.repo.save(empToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> delete(@PathVariable int id){
        Publisher empToBeDeleted = this.repo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This is not found by dave!"));
        this.repo.delete(empToBeDeleted);
        return ResponseEntity.ok(empToBeDeleted);
    }
}
