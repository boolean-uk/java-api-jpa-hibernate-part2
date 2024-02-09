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
        public List<Author> getALlAuthor() {
            return this.authorRepository.findAll();
        }

        @PostMapping
        public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
            if(author.getFirstName() == null || author.getLastName() == null || author.getEmail()== null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not create author, please check all required fields are correct");
            }
            return new ResponseEntity<>(this.authorRepository.save(author), HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Author> getAuthorById(@PathVariable int id) {
            Author author = this.authorRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No authors with that id were found"));
            return ResponseEntity.ok(this.getAnAuthor(id));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Author> deleteAuthor(@PathVariable int id) {

            Author authorToDelete =  this.authorRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors with that id were found"));
            this.authorRepository.delete(authorToDelete);
            authorToDelete.setBooks(new ArrayList<Book>());
            return ResponseEntity.ok(authorToDelete);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody Author author) {
            if(author.getFirstName() == null || author.getLastName() == null || author.getEmail()== null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Could not update author, please check all required fields are correct");
            }

            Author authorToUpdate = this.authorRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No authors with that id were found"));
            authorToUpdate.setFirstName(author.getFirstName());
            authorToUpdate.setLastName(author.getLastName());
            authorToUpdate.setEmail(author.getEmail());
            authorToUpdate.setAlive(author.isAlive());
            return new ResponseEntity<>(this.authorRepository.save(authorToUpdate), HttpStatus.CREATED);
        }

        private Author getAnAuthor(int id) {
            return this.authorRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with that id were found"));
        }
}
