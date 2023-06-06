package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @GetMapping()
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(bookRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id){
        Book foundBook = bookRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not found"));

        return new ResponseEntity<>(foundBook, HttpStatus.OK);
    }
    public record BookRequest(String title,String genre,int author_id,int publisher_id){}
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody BookRequest bookRequest){
        Author author = authorRepository
                .findById(bookRequest.author_id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Author not found"));

        Publisher publisher = publisherRepository
                .findById(bookRequest.publisher_id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Publisher not found"));

        return new ResponseEntity<>(bookRepository.save(new Book(
                bookRequest.title,
                bookRequest.genre,
                author,
                publisher))
                , HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody BookRequest bookRequest ){
        Book foundBook = bookRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not found"));

        Author author = authorRepository
                .findById(bookRequest.author_id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Author not found"));

        Publisher publisher = publisherRepository
                .findById(bookRequest.publisher_id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Publisher not found"));

        foundBook.setTitle(bookRequest.title);
        foundBook.setGenre(bookRequest.genre);
        foundBook.setAuthor(author);
        foundBook.setPublisher(publisher);

        return new ResponseEntity<>(bookRepository.save(foundBook), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Book foundBook = bookRepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not found"));

        bookRepository.delete(foundBook);
        return new ResponseEntity<>(foundBook, HttpStatus.OK);
    }
}
