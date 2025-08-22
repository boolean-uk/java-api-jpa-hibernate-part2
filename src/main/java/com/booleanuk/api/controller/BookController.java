package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.view.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//
@RestController
@RequestMapping("books")
public class BookController {
	private final BookRepository bookRepository;

	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@GetMapping
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(bookRepository.findAll());
	}

	@GetMapping("{id}")
	public ResponseEntity<?> findOne(@PathVariable int id){
		Book book = bookRepository.findById(id).orElse(null);
		if (book == null){
			return ResponseEntity.notFound().build();
		}

		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	public boolean incompleteBook(Book book) {
		try {
			// Check if essential fields are null
			return book.getTitle() == null
					|| book.getGenre() == null
					|| book.getAuthor() == null;
		} catch (Exception e) {
			return false;
		}
	}

	@PostMapping
	public ResponseEntity<?> postOne(@RequestParam Book book){
		if (incompleteBook(book)){
			return ResponseEntity.badRequest().build();
		}
		return new ResponseEntity<>(bookRepository.save(book), HttpStatus.CREATED);


	}

	@PutMapping("{id}")
	public ResponseEntity<?> putOne(@PathVariable int id, @RequestBody Book book) {
		// Check if the book exists by id
		Book foundBook = bookRepository.findById(id).orElse(null);
		if (foundBook == null) {
			return ResponseEntity.notFound().build();
		}

		// Validate the Book object
		if (incompleteBook(book)) {
			return ResponseEntity.badRequest().build();
		}

		// Update the book
		foundBook.setTitle(book.getTitle());
		foundBook.setGenre(book.getGenre());
		foundBook.setAuthor(book.getAuthor());
		foundBook.setPublisher(book.getPublisher());

		return new ResponseEntity<>(bookRepository.save(foundBook), HttpStatus.OK);
	}


	@DeleteMapping
 	public ResponseEntity<?> deleteOne(@PathVariable int id){
		Book foundBook = bookRepository.findById(id).orElse(null);
		if (foundBook == null) {
			return ResponseEntity.notFound().build();
		}
		bookRepository.delete(foundBook);
		return new ResponseEntity<>(bookRepository.save(foundBook), HttpStatus.OK);
	}
}
