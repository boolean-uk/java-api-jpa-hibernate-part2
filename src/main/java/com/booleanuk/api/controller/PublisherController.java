package com.booleanuk.api.controller;

import com.booleanuk.api.model.Publisher;
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
    private PublisherRepository publisherRepository;

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers(){

        return new ResponseEntity<>(publisherRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Publisher> getOnePublisher(@PathVariable int id){
        Publisher publisher = publisherRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }


    @PutMapping("{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable int id ,@RequestBody Publisher publisher) {

        Publisher publisherToUpdate = publisherRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(publisher.getName().isBlank() || publisher.getLocation().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Check all required fields are correct");

        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());

        return new ResponseEntity<>(publisherRepository.save(publisherToUpdate), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable int id) {

        Publisher publisherToDelete = publisherRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        publisherRepository.delete(publisherToDelete);

        return new ResponseEntity<>(publisherToDelete, HttpStatus.OK);

    }



}
