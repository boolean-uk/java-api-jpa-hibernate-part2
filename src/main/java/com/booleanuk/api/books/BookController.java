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
    private BookRepository books;

    @Autowired
    private AuthorRepository authors;

    @Autowired
    private PublisherRepository publishers;

    @GetMapping
    public List<Book> getAll(){
        return books.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getOne(@PathVariable int id){
        return ResponseEntity.ok(getByID(id));
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book){
        Author tempAuthor = authors
                .findById(book.getAuthor().getId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with this ID."));

        Publisher tempPublisher = publishers
                .findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with this ID."));

        book.setAuthor(tempAuthor);
        book.setPublisher(tempPublisher);

        return new ResponseEntity<Book>(books.save(book), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> update(@PathVariable int id, @RequestBody Book book){
        Book toUpdate = getByID(id);
        Author tempAuthor = authors
                .findById(book.getAuthor().getId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with this ID."));

        Publisher tempPublisher = publishers
                .findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with this ID."));

        toUpdate.setTitle(book.getTitle());
        toUpdate.setGenre(book.getGenre());
        toUpdate.setAuthor(tempAuthor);
        toUpdate.setPublisher(tempPublisher);

        return new ResponseEntity<Book>(books.save(toUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> delete(@PathVariable int id){
        Book toDelete = getByID(id);
        books.delete(toDelete);

        return ResponseEntity.ok(toDelete);
    }

    private Book getByID(int id){
        return books
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
