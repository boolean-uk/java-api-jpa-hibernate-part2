package com.booleanuk.api.publishers;

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
    private PublisherRepository publishers;

    @GetMapping
    public List<Publisher> getAll(){
        return publishers.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getOne(@PathVariable int id){
        return ResponseEntity.ok(getByID(id));
    }

    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher publisher){
        if (publishers.existsByNameAndLocation(publisher.getName(), publisher.getLocation())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This publisher is already registered.");
        }

        return new ResponseEntity<Publisher>(publishers.save(publisher), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> update(@PathVariable int id, @RequestBody Publisher publisher){
        Publisher toUpdate = getByID(id);

        if (publishers.existsByNameAndLocation(publisher.getName(), publisher.getLocation())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This publisher is already registered.");
        }

        toUpdate.setName(publisher.getName());
        toUpdate.setLocation(publisher.getLocation());

        return new ResponseEntity<Publisher>(publishers.save(toUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> delete(@PathVariable int id){
        Publisher toDelete = getByID(id);
        publishers.delete(toDelete);

        return ResponseEntity.ok(toDelete);
    }

    private Publisher getByID(int id){
        return publishers
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
