package com.booleanuk.api.controller;

import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("publishers")
public class PublisherController {
    @Autowired
    private PublisherRepository publisherRepository;
    @GetMapping()
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(publisherRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id){
        Publisher foundPublisher =publisherRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Publisher not found"));

        return new ResponseEntity<>(foundPublisher, HttpStatus.OK);
    }
    public record PublisherRequest(String name,String location){};
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody PublisherRequest publisherRequest){
        return new ResponseEntity<>(publisherRepository.save(
                new Publisher(publisherRequest.name,publisherRequest.location))
                , HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id,@RequestBody PublisherRequest publisherRequest){
        Publisher foundPublisher =publisherRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Publisher not found"));
        foundPublisher.setName(publisherRequest.name);
        foundPublisher.setLocation(publisherRequest.location);

        return new ResponseEntity<>(publisherRepository.save(foundPublisher), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Publisher foundPublisher =publisherRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Publisher not found"));
        publisherRepository.delete(foundPublisher);

        return new ResponseEntity<>(foundPublisher, HttpStatus.OK);
    }
}
