package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Publisher;
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
    private PublisherRepository publisherRepository;

    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher publisher){
        return new ResponseEntity<>(this.publisherRepository.save(publisher), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Publisher> getAll(){
        return this.publisherRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getOne(@PathVariable int id){
        Publisher publisher = this.publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id")
        );
        return ResponseEntity.ok(publisher);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> update(@PathVariable int id, @RequestBody Publisher publisher){
        Publisher publisherToUpdate = this.publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id")
        );
        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());
        return new ResponseEntity<>(this.publisherRepository.save(publisherToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> delete(@PathVariable int id){
        Publisher publisherToDelete = this.publisherRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id")
        );
        this.publisherRepository.delete(publisherToDelete);
        return ResponseEntity.ok(publisherToDelete);
    }
}
