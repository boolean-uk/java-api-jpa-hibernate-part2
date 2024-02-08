package com.booleanuk.api.author;

import com.booleanuk.api.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("authors")
public class AuthorController {


    @Autowired
    private AuthorRepository authorRepository;
    
    @GetMapping
    public List<Author> getAllAuthors(){
        return this.authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable int id){
        Author author = this.authorRepository
                .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id were found"));
        return ResponseEntity.ok(author);
    }
    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {

        if (author.getFirstName() == null || author.getLastName() == null || author.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create author, please check all required fields are correct");
        }

        Author createdAuthor = this.authorRepository.save(author);
        createdAuthor.setBooks(new ArrayList<>());
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {
        Author authorToDelete = this.getAnAuthor(id);
        this.authorRepository.delete(authorToDelete);
        authorToDelete.setBooks(new ArrayList<Book>());
        return ResponseEntity.ok(authorToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> deleteAuthorById(@PathVariable int id, @RequestBody Author author){


        if (author.getFirstName() == null || author.getLastName() == null || author.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the author, please check all required fields are correct");
        }


        Author authorToUpdate = null;
        authorToUpdate = this.authorRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id were found"));
        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setEmail(author.getEmail());
        authorToUpdate.setAlive(author.isAlive());
        return new ResponseEntity<>(this.authorRepository.save(authorToUpdate), HttpStatus.CREATED);
    }


    private Author getAnAuthor(int id){
        return this.authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id were found"));
    }
}
