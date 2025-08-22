package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.view.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("authors")
public class AuthorController {
	private final AuthorRepository authorRepository;

	public AuthorController(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@GetMapping
	public ResponseEntity<?> getAll(){
		return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getOne(@PathVariable int id){
		Author author = authorRepository.findById(id).orElse(null);
		if (author == null){
			return ResponseEntity.notFound().build();
		}

		return new ResponseEntity<>(author, HttpStatus.OK);
	}
	public boolean incompleteAuthor(Author author) {
		try {
			return author.getFirstName() == null || author.getLastName() == null ||
					author.getEmail() == null;
		} catch (Exception e){
			return false;
		}
	}

	@PostMapping
	public ResponseEntity<?> postOne(@RequestParam Author author){
		if (incompleteAuthor(author)){
			return ResponseEntity.badRequest().build();
		}
		return new ResponseEntity<>(authorRepository.save(author), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<?> putOne(@PathVariable int id, @RequestParam Author author){
		// check if found by id
		Author foundAuthor = authorRepository.findById(id).orElse(null);
		if (foundAuthor == null){
			return ResponseEntity.notFound().build();
		}

		// check if valid author body
		if (incompleteAuthor(author)){
			return ResponseEntity.badRequest().build();
		}

		//update
		foundAuthor.setFirstName(author.getFirstName());
		foundAuthor.setLastName(author.getLastName());
		foundAuthor.setEmail(author.getEmail());
		foundAuthor.setAlive(author.isAlive());
		// foundAuthor.setBooks(author.getBooks()); // just update information. Not books?



		return new ResponseEntity<>(authorRepository.save(foundAuthor), HttpStatus.CREATED);

	}


	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteOne(@PathVariable int id){
		Author author = authorRepository.findById(id).orElse(null);
		if (author == null){
			return ResponseEntity.notFound().build();
		}

		authorRepository.delete(author);

		return new ResponseEntity<>(author, HttpStatus.OK);
	}


}
