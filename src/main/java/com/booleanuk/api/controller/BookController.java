package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
import com.booleanuk.api.repository.PublisherRepository;
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
    BookRepository repo;

    @Autowired
    PublisherRepository publisherRepo;

    @Autowired
    AuthorRepository authorRepo;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return new ResponseEntity<>(
                repo.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getOne(@PathVariable(name="id") int id) {
        return new ResponseEntity<>(
                repo.findById(id).orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No books with that id were found"
                        )
                ),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Book> createOne(@RequestBody Book book) {
        authorRepo.findByFirstNameAndLastNameAndEmailAndAlive(
                book.getAuthor().getFirstName(),
                book.getAuthor().getLastName(),
                book.getAuthor().getEmail(),
                book.getAuthor().isAlive()
        ).ifPresent(book::setAuthor);

        publisherRepo.findByNameAndLocation(
                book.getPublisher().getName(),
                book.getPublisher().getLocation()
        ).ifPresent(book::setPublisher);


        return new ResponseEntity<>(
                repo.save(book),
                HttpStatus.CREATED
        );
    }

    @PutMapping("{id}")
    public ResponseEntity<Book> updateOne(@PathVariable(name="id") int id, @RequestBody Book book) {
        Book requested = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No books with that id were found"
                )
        );

        requested.setTitle(book.getTitle());
        requested.setGenre(book.getGenre());

        authorRepo.findByFirstNameAndLastNameAndEmailAndAlive(
                book.getAuthor().getFirstName(),
                book.getAuthor().getLastName(),
                book.getAuthor().getEmail(),
                book.getAuthor().isAlive()
        ).ifPresentOrElse(
                requested::setAuthor,
                () -> requested.setAuthor(book.getAuthor())
        );

        publisherRepo.findByNameAndLocation(
                book.getPublisher().getName(),
                book.getPublisher().getLocation()
        ).ifPresentOrElse(
                requested::setPublisher,
                () -> requested.setPublisher(book.getPublisher())
        );

        return new ResponseEntity<>(
                repo.save(requested),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteOne(@PathVariable(name="id") int id) {
        Book deleted = repo.findById(id)
                                .orElseThrow(() ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "No employees with that id were found"
                                        )
                                );

        repo.deleteById(id);

        return new ResponseEntity<>(
                deleted,
                HttpStatus.OK
        );
    }
}
