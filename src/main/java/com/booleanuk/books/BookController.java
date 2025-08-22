package com.booleanuk.books;

import java.util.List;
import com.booleanuk.publishers.Publisher;
import com.booleanuk.authors.Author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.booleanuk.authors.AuthorRepository;
import com.booleanuk.publishers.PublisherRepository;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(this.bookRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Author author = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id exists"));

        Publisher publisher = this.publisherRepository.findById(book.getPublisher().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id exists"));

        book.setAuthor(author);
        book.setPublisher(publisher);
        return new ResponseEntity<Book>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getOneBook(@PathVariable int id) {
        return ResponseEntity.ok(this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find book with this id")));
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find book with this id"));
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());

        Author newAuthor = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id exists"));

        Publisher newPublisher = this.publisherRepository.findById(book.getPublisher().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id exists"));

        bookToUpdate.setAuthor(newAuthor);
        bookToUpdate.setPublisher(newPublisher);

        return new ResponseEntity<Book>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find book with this id"));

        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }
}
