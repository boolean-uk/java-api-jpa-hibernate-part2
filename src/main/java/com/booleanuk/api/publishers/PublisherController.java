package com.booleanuk.api.publishers;

import com.booleanuk.api.books.Book;
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
        Publisher created = publishers.save(publisher);
        created.setBooks(new ArrayList<Book>());

        return new ResponseEntity<Publisher>(created, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> update(@PathVariable int id, @RequestBody Publisher publisher){
        Publisher toUpdate = getByID(id);
        toUpdate.setName(publisher.getName());
        toUpdate.setLocation(publisher.getLocation());

        return new ResponseEntity<Publisher>(publishers.save(toUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> delete(@PathVariable int id){
        Publisher toDelete = getByID(id);
        publishers.delete(toDelete);
        toDelete.setBooks(new ArrayList<Book>());

        return ResponseEntity.ok(toDelete);
    }

    private Publisher getByID(int id){
        return publishers
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
