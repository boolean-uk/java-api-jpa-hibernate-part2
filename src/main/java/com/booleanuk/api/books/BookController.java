package com.booleanuk.api.books;

import com.booleanuk.api.authors.Author;
import com.booleanuk.api.authors.AuthorRepository;
import com.booleanuk.api.authors.Publisher;
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
    private  BookRepository bookRepository;
    @Autowired
    private  AuthorRepository authorRepository;
    @Autowired
    private  PublisherRepository publisherRepository;

    @GetMapping
    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getById(@PathVariable("id") int id) {
        Book book = null;
        book = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with that id was found"));
        return ResponseEntity.ok(book);
    }

    record PostBook(String title, String genre, Author author, Publisher publisher) {}

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Book create(@RequestBody PostBook request) {
        Author author = null;
        author = this.authorRepository.findById(request.author.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
        Publisher publisher = null;
        publisher = this.publisherRepository.findById(request.publisher.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));

        Book book = new Book(request.title(), request.genre, request.author, request.publisher);


        book.setAuthor(author);
        book.setPublisher(publisher);
        return this.bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateAuthor(@PathVariable int id, @RequestBody Book book) {
        Book bookToUpdate = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with that id was found"));
        Author author = null;
        author = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
        Publisher publisher = null;
        publisher = this.publisherRepository.findById(book.getPublisher().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));

        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setAuthor(author);
        bookToUpdate.setPublisher(publisher);

        return new ResponseEntity<Book>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with that id was found"));
        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }
}
