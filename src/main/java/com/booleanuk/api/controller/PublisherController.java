package com.booleanuk.api.controller;

import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.PublisherRepository;
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
    PublisherRepository rep;
    
    @GetMapping
    public ResponseEntity<List<Publisher>> getAll() {
        return ResponseEntity.ok(rep.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getById(@PathVariable Integer id) {
        Publisher publisher = this.rep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find Publisher by ID"));
        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher publisher) {
        return new ResponseEntity<>(this.rep.save(publisher), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> delete(@PathVariable Integer id) {
        Publisher deletepublisher = this.rep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find Publisher by ID"));
        this.rep.delete(deletepublisher);
        return ResponseEntity.ok(deletepublisher);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> update(@PathVariable Integer id, @RequestBody Publisher publisher){
        Publisher oldPublisher = getById(id).getBody();

        oldPublisher.setName(publisher.getName());
        oldPublisher.setLocation(publisher.getLocation());

        return new ResponseEntity<>(this.rep.save(oldPublisher), HttpStatus.CREATED);
    }
}
