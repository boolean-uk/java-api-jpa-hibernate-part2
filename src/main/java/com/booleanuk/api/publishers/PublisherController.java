package com.booleanuk.api.publishers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<Publisher> getAll() {
        return this.publisherRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getOne(@PathVariable int id) {
        Publisher publisher = this.publisherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher found"));

        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PublisherDTO> addOne(@Valid @RequestBody Publisher publisher) {
        return new ResponseEntity<>(
                modelMapper.map(this.publisherRepository.save(publisher), PublisherDTO.class),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO> updateOne(@PathVariable int id, @Valid @RequestBody Publisher publisher) {
        Publisher publisherToUpdate = this.publisherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher found"));

        publisherToUpdate.setName(publisher.getName());
        publisherToUpdate.setLocation(publisher.getLocation());

        return new ResponseEntity<>(
                modelMapper.map(this.publisherRepository.save(publisherToUpdate), PublisherDTO.class),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PublisherDTO> deleteOne(@PathVariable int id){
        Publisher publisher = this.publisherRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher found"));

        try{
            this.publisherRepository.delete(publisher);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Publisher has books connected to it");
        }
        return new ResponseEntity<>(modelMapper.map(publisher, PublisherDTO.class), HttpStatus.OK);
    }
}