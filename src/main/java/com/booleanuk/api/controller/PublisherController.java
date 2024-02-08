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
    public List<Publisher> getAllPublishers(){
        return this.publisherRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getById(@PathVariable("id") Integer id) {
        Publisher publisher = this.publisherRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find "));

        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<Publisher> create(@RequestBody Publisher publisher) {
        //Regex for the strings
        String regex = "/^[a-zA-Z\\s]*$/";
        if(!publisher.getName().matches(regex) && !publisher.getLocation().matches(regex)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write the fields correctly");
        }

        Publisher createdPublisher = this.publisherRepository.save(publisher);
        createdPublisher.setBooks(new ArrayList<>());
        return new ResponseEntity<>(createdPublisher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updateAPublisher(@PathVariable int id,@RequestBody Publisher publisher){
        Publisher publisherToUpdate = this.publisherRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the publisher...."));

        //Regex for the strings
        String regex = "/^[a-zA-Z\\s]*$/";
        if(!publisher.getName().matches(regex) && !publisher.getLocation().matches(regex)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Write the fields correctly");
        }
        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());

        return new ResponseEntity<>(this.publisherRepository.save(publisherToUpdate),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deleteAPublisher(@PathVariable int id){
        Publisher publisherToDelete = this.publisherRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cant find the publisher!!!"));
        publisherToDelete.setBooks(new ArrayList<>());
        this.publisherRepository.delete(publisherToDelete);
        return ResponseEntity.ok(publisherToDelete);
    }
}
