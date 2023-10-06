package com.booleanuk.api.book;

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
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable int id) {
        Book book = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "book not found"));
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        if (book.getTitle() == null || book.getGenre() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
        }
        return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        if (book.getTitle() == null || book.getGenre() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some fields are invalid");
        } else {
            Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found to update"));

            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setGenre(bookToUpdate.getGenre());
            bookToUpdate.setAuthor(book.getAuthor());
            bookToUpdate.setPublisher(book.getPublisher());

            return new ResponseEntity<>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found to delete"));
        this.bookRepository.delete(bookToDelete);

        return ResponseEntity.ok(bookToDelete);
    }
}
