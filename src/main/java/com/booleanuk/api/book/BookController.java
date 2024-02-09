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
    private PublisherRepository publisherRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Book> getALlBook() {
        return this.bookRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {

        Author author = this.authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not create book, please check all required fields are correct"));

        Publisher publisher = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not create book, please check all required fields are correct"));

        book.setAuthor(author);
        book.setPublisher(publisher);

        return new ResponseEntity<Book>(this.bookRepository.save(book),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = this.bookRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No book with that id were found"));

        return ResponseEntity.ok(this.getABook(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Book bookToDelete =  this.bookRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No books with that id were found"));
        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Book updateBook = getABook(id);

        Author tempAuthor = this.authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        Publisher tempPublisher = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));

        updateBook.setTitle(book.getTitle());
        updateBook.setGenre(book.getGenre());
        updateBook.setAuthor(tempAuthor);
        updateBook.setPublisher(tempPublisher);

        return new ResponseEntity<Book>(this.bookRepository.save(updateBook),
                HttpStatus.CREATED);
    }


    private Book getABook(int id) {
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No books with that id were found"));
    }
}