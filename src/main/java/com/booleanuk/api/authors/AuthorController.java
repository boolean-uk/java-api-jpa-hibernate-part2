package com.booleanuk.api.authors;


import com.booleanuk.api.books.Book;
import com.booleanuk.api.books.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;


    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable int id) {
        Author author = this.authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        return new ResponseEntity<>(this.authorRepository.save(author), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable(name = "id") int id, @RequestBody Author author) {
        Author authorToUpdate = this.authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such author."));
        try {
            authorToUpdate.setEmail(author.getEmail());
            authorToUpdate.setFirst_name(author.getFirst_name());
            authorToUpdate.setLast_name(author.getLast_name());
            authorToUpdate.setAlive(author.isAlive());
            return new ResponseEntity<>(this.authorRepository.save(authorToUpdate), HttpStatus.OK);
        }
        catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the author. Error: "+ e);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable(name = "id") int id) {
        Author toDelete = this.authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such author found"));
        this.bookRepository.deleteAll(toDelete.getBooks());
        this.authorRepository.delete(toDelete);
        return ResponseEntity.ok(toDelete);

    }

}


