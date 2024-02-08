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
	PublisherRepository publisherRepository;

	@GetMapping
	public List<Publisher> getAll(){
		return publisherRepository.findAll();
	}
	@PostMapping
	public ResponseEntity<Publisher> create(@RequestBody Publisher publisher){
		Publisher newPublisher = new Publisher(publisher.getName(), publisher.getLocation());
		return new ResponseEntity<>(publisherRepository.save(newPublisher), HttpStatus.CREATED);
	}
	@GetMapping("{id}")
	public ResponseEntity<Publisher> get(@PathVariable int id){
		Publisher publisher = publisherRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
		return ResponseEntity.ok(publisher);
	}
	@PutMapping("{id}")
	public ResponseEntity<Publisher> update(@PathVariable int id,@RequestBody Publisher publisher){
		Publisher newPublisher = publisherRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
		newPublisher.setName(publisher.getName());
		newPublisher.setLocation(publisher.getLocation());
		return new ResponseEntity<>(publisherRepository.save(publisher),HttpStatus.CREATED);
	}
	@DeleteMapping("{id}")
	public ResponseEntity<Publisher> delete(@PathVariable int id){
		Publisher publisher = publisherRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
		publisherRepository.delete(publisher);
		return ResponseEntity.ok(publisher);
	}
}
