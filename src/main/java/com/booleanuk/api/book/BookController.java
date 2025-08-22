package com.booleanuk.api.book;

import com.booleanuk.api.author.Author;
import com.booleanuk.api.author.AuthorRepository;
import com.booleanuk.api.publisher.Publisher;
import com.booleanuk.api.publisher.PublisherRepository;
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

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Author author = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id exists"));
        Publisher publisher = this.publisherRepository.findById(book.getPublisher().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id exists"));
        book.setAuthor(author);
        book.setPublisher(publisher);

        return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable int id) {
        return new ResponseEntity<>(this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No book with that id were found")), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the book, please check all required fields are correct");
        }

        Author author = this.authorRepository.findById(book.getAuthor().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id exists"));
        Publisher publisher = this.publisherRepository.findById(book.getPublisher().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id exists"));
        book.setAuthor(author);
        book.setPublisher(publisher);

        Book updateBook = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"No book with that id were found "));
        updateBook.setTitle(book.getTitle());
        updateBook.setGenre(book.getGenre());
        updateBook.setAuthor(book.getAuthor());
        updateBook.setPublisher(book.getPublisher());


        return new ResponseEntity<>(this.bookRepository.save(updateBook), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book deletedBook = this.bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with that id were found"));
        this.bookRepository.delete(deletedBook);
        return ResponseEntity.ok(deletedBook);
    }
}
