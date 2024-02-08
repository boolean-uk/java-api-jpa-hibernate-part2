package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {
	@Autowired
	AuthorRepository authorRepository;

	@GetMapping
	public List<Author> getAll(){
		return authorRepository.findAll();
	}
	@PostMapping
	public ResponseEntity<Author> create(@RequestBody Author author){
		Author newAuthor= new Author(author.getFirst_name(), author.getLast_name(),author.getEmail(),author.isAlive());
		return new ResponseEntity<>(authorRepository.save(newAuthor), HttpStatus.CREATED);
	}
	@GetMapping("{id}")
	public ResponseEntity<Author> get(@PathVariable int id){
		Author author = authorRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
		return ResponseEntity.ok(author);
	}
	@PutMapping("{id}")
	public ResponseEntity<Author> update(@PathVariable int id,@RequestBody Author author){
		Author newAuthor = authorRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
		newAuthor.setFirst_name(author.getFirst_name());
		newAuthor.setLast_name(author.getLast_name());
		newAuthor.setEmail(author.getEmail());
		newAuthor.setAlive(author.isAlive());
		return new ResponseEntity<>(authorRepository.save(newAuthor),HttpStatus.CREATED);
	}
	@DeleteMapping("{id}")
	public ResponseEntity<Author> delete(@PathVariable int id){
		Author author = authorRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
		authorRepository.delete(author);
		return ResponseEntity.ok(author);
	}
}
