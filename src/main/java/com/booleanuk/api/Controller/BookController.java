package com.booleanuk.api.Controller;

import com.booleanuk.api.Model.Book;
import com.booleanuk.api.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Book newBook) {
        try {
            Book savedBook = this.bookRepository.save(newBook);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Could not create book, please check all required fields are correct.");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        Optional<Book> book = this.bookRepository.findById(id);
        if (book.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(book.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No books with that id were found.");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody Book updatedBook) {
        Optional<Book> existingBookOptional = this.bookRepository.findById(id);

        if (existingBookOptional.isPresent()) {
            try {
                Book existingBook = existingBookOptional.get();
                existingBook.setTitle(updatedBook.getTitle());
                existingBook.setGenre(updatedBook.getGenre());
                existingBook.setAuthor_id(updatedBook.getAuthor_id());
                existingBook.setPublisher_id(updatedBook.getPublisher_id());
                Book savedBook = this.bookRepository.save(existingBook);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Could not update book, please check all required fields are correct.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No books with that id were found.");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public Book delete(@PathVariable("id") Integer id) {
        Book returnBook = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No books with that ID were found")
        );
        this.bookRepository.deleteById(id);
        return returnBook;
    }
}