package com.booleanuk.api.controller;

import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.view.PublisherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("publishers")
public class PublisherController {
	private final PublisherRepository publisherRepository;

	public PublisherController(PublisherRepository publisherRepository) {
		this.publisherRepository = publisherRepository;
	}

	@GetMapping
	public ResponseEntity<?> getAllPublishers(){
		return ResponseEntity.ok(publisherRepository.findAll());
	}

	@GetMapping("{id}")
	public ResponseEntity<?> findOneById(@PathVariable int id){
		Publisher publisher = publisherRepository.findById(id).orElse(null);
		if (publisher == null){
			return ResponseEntity.notFound().build();
		}

		return new ResponseEntity<>(publisher, HttpStatus.OK);
	}

	public boolean incompletePublisher(Publisher publisher) {
		try {
			return publisher.getName() == null
					|| publisher.getLocation() == null;
		} catch (Exception e) {
			return false;
		}
	}

	@PostMapping
	public ResponseEntity<?> postOnePublisher(@RequestParam Publisher publisher){
		if (incompletePublisher(publisher)){
			return ResponseEntity.badRequest().build();
		}
		return new ResponseEntity<>(publisherRepository.save(publisher), HttpStatus.CREATED);


	}

	@PutMapping("{id}")
	public ResponseEntity<?> putOne(@PathVariable int id, @RequestBody Publisher publisher) {
		// Check if the book exists by id
		Publisher foundPublisher = publisherRepository.findById(id).orElse(null);
		if (foundPublisher == null) {
			return ResponseEntity.notFound().build();
		}

		// Validate the Book object
		if (incompletePublisher(publisher)) {
			return ResponseEntity.badRequest().build();
		}

		// Update the book
		foundPublisher.setName(publisher.getName());
		foundPublisher.setLocation(publisher.getLocation());

		return new ResponseEntity<>(publisherRepository.save(foundPublisher), HttpStatus.OK);
	}


	@DeleteMapping
	public ResponseEntity<?> deleteOne(@PathVariable int id){
		Publisher foundPublisher = publisherRepository.findById(id).orElse(null);
		if (foundPublisher == null) {
			return ResponseEntity.notFound().build();
		}
		publisherRepository.delete(foundPublisher);
		return new ResponseEntity<>(publisherRepository.save(foundPublisher), HttpStatus.OK);
	}
}
