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

    @GetMapping
    public List<Book> getAllBooks(){
        return this.bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id){
        Book book = this.bookRepository
                .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No books with that id were found"));
        return ResponseEntity.ok(book);
    }
    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {

        if (book.getTitle() == null || book.getGenre() == null || book.getAuthor() == null || book.getPublisher() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create book, please check all required fields are correct");
        }


        Author tempAuthor = this.authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id were found"));

        Publisher tempPublisher = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id were found"));

        book.setAuthor(tempAuthor);
        book.setPublisher(tempPublisher);
        return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBookById(@PathVariable int id){
        Book bookToDelete = null;
        bookToDelete = this.bookRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No books with that id were found"));
        this.bookRepository.delete(bookToDelete);
        return ResponseEntity.ok(bookToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> deleteBookById(@PathVariable int id, @RequestBody Book book){

        if (book.getTitle() == null || book.getGenre() == null || book.getAuthor() == null || book.getPublisher() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the book, please check all required fields are correct");
        }

        Author tempAuthor = this.authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id were found"));

        Publisher tempPublisher = this.publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with that id were found"));

        book.setAuthor(tempAuthor);
        book.setPublisher(tempPublisher);

        Book bookToUpdate = null;

        bookToUpdate = this.bookRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No books with that id were found"));
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setGenre(book.getGenre());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setPublisher(book.getPublisher());
        return new ResponseEntity<>(this.bookRepository.save(bookToUpdate), HttpStatus.CREATED);
    }


    private Book getABook(int id){
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No books with that id were found"));
    }
}
