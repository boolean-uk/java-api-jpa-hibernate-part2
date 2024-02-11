package com.booleanuk.api.controller;
import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("publishers")
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public List<Publisher> getAll(){
        return this.publisherRepository.findAll();
    }
    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher employee){
        return new ResponseEntity<Publisher>(this.publisherRepository.save(employee), HttpStatus.CREATED) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getById(@PathVariable int id){
        Publisher employee = this.publisherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        return ResponseEntity.ok(employee);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> delete(@PathVariable int id) {
        Publisher toDelete = this.publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No publisher with ID"));
        this.publisherRepository.delete(toDelete);
        toDelete.setBooks(new ArrayList<Book>());
        return ResponseEntity.ok(toDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> update(@PathVariable int id, @RequestBody Publisher employee){
        Publisher update = this.publisherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
        update.setName(employee.getName());
        update.setLocation(employee.getLocation());
        update.setBooks(new ArrayList<>());
        return new ResponseEntity<>(this.publisherRepository.save(update), HttpStatus.CREATED);
    }
}
