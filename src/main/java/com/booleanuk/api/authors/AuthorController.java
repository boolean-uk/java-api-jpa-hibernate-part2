package com.booleanuk.api.authors;

import com.booleanuk.api.books.Book;
import com.booleanuk.api.books.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok(this.authorRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        validateAuthorOrThrowException(author);

        Author newAuthor = this.authorRepository.save(author);

        newAuthor.setBooks(new ArrayList<Book>());

        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable int id) {
        Author author = findAuthorOrThrowException(id);

        return ResponseEntity.ok(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
        validateAuthorOrThrowException(author);

        Author authorToBeUpdated = findAuthorOrThrowException(id);

        authorToBeUpdated.setFirstName(author.getFirstName());
        authorToBeUpdated.setLastName(author.getLastName());
        authorToBeUpdated.setEmail(author.getEmail());
        authorToBeUpdated.setAlive(author.isAlive());

        this.authorRepository.save(authorToBeUpdated);

        return new ResponseEntity<>(authorToBeUpdated, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        Author authorToBeDeleted = findAuthorOrThrowException(id);

        this.authorRepository.deleteById(id);

        authorToBeDeleted.setBooks(new ArrayList<Book>());

        return ResponseEntity.ok(authorToBeDeleted);
    }

    private Author findAuthorOrThrowException(int id) {
        return this.authorRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors with that id were found."));
    }

    private void validateAuthorOrThrowException(Author author) {
        if(author.getFirstName() == null || author.getLastName() == null || author.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create/update the author, please check all required fields are correct.");
        }
    }
}
