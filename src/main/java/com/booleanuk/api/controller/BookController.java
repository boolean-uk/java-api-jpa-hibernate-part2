package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {
	@Autowired
	BookRepository bookRepository;
	@Autowired
	PublisherRepository publisherRepository;
	@Autowired
	AuthorRepository authorRepository;

	@GetMapping
	public List<Book> getAll(){
		return bookRepository.findAll();
	}
	@PostMapping
	public ResponseEntity<Book> create(@RequestBody Book book){
		Book newBook = new Book(book.getTitle(), book.getGenre(),book.getAuthor(),book.getPublisher());
		return new ResponseEntity<>(bookRepository.save(newBook), HttpStatus.CREATED);
	}
	@GetMapping("{id}")
	public ResponseEntity<Book> get(@PathVariable int id){
		Book book = bookRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
		return ResponseEntity.ok(book);
	}
	@PutMapping("{id}")
	public ResponseEntity<Book> update(@PathVariable int id,@RequestBody Book book){
		Author author = authorRepository.findById(book.getAuthor().getId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
		Publisher publisher = publisherRepository.findById(book.getPublisher().getId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
		Book newBook = bookRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
		newBook.setTitle(book.getTitle());
		newBook.setGenre(book.getGenre());
		newBook.setAuthor(author);
		newBook.setPublisher(publisher);
		return new ResponseEntity<>(bookRepository.save(book),HttpStatus.CREATED);
	}
	@DeleteMapping("{id}")
	public ResponseEntity<Book> delete(@PathVariable int id){
		Book book = bookRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
		bookRepository.delete(book);
		return ResponseEntity.ok(book);
	}
}
