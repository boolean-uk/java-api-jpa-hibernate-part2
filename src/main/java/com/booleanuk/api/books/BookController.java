package com.booleanuk.api.books;

import com.booleanuk.api.authors.Author;
import com.booleanuk.api.authors.AuthorRepository;
import com.booleanuk.api.publishers.Publisher;
import com.booleanuk.api.publishers.PublisherRepository;
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
    AuthorRepository authorRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(this.bookRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getOneBook(@PathVariable(name = "id") int id) {
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Author author = this.authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that ID exists."));

        Publisher publisher = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that ID exists."));

        book.setAuthor(author);
        book.setPublisher(publisher);
        return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable(name = "id") int id, @RequestBody Book book) {
        Author author = this.authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that ID exists."));

        Publisher publisher = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that ID exists."));

        Book bookToUpdate = this.bookRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with that ID exists"));
        try {
            bookToUpdate.setGenre(book.getGenre());
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setAuthor(author);
            bookToUpdate.setPublisher(publisher);
            return new ResponseEntity<>(this.bookRepository.save(bookToUpdate), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the book. Error: " + e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable(name = "id") int id) {
        Book toDelete = this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such book."));
        this.bookRepository.delete(toDelete);
        return ResponseEntity.ok(toDelete);
    }



}
