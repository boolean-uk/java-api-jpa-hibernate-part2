package com.booleanuk.api.Controller;

import com.booleanuk.api.Model.Book;
import com.booleanuk.api.Model.Publisher;
import com.booleanuk.api.Repository.BookRepository;
import com.booleanuk.api.Repository.PublisherRepository;
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
    private final PublisherRepository repository;

    public PublisherController(PublisherRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody Publisher publisher){
        try {
            return new ResponseEntity<Publisher>(this.repository.save(publisher),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create publisher, please check all required fields are correct.");
        }
    }

    @GetMapping
    public List<Publisher> getAll() {
        return this.repository.findAll();
    }



    @GetMapping("{id}")
    public Publisher getById(@PathVariable("id") Integer id) {
        return this.repository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No publisher with that ID found")
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id,
                                           @RequestBody Publisher publisher){
        Publisher publisherToUpdate=this.repository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No publisher with that ID found")
        );
        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());
        return new ResponseEntity<Publisher>(this.repository.save(publisherToUpdate
        ), HttpStatus.CREATED);



    }

    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id){
        Publisher publisherToDelete=this.repository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No publisher with that ID found")
        );

        this.repository.delete(publisherToDelete);
        return ResponseEntity.ok(publisherToDelete);
    }
}
