package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.ModelDtos.*;
import com.booleanuk.api.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository repo;

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody Author author) {
        try {
            Author savedAuth = repo.save(author);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not create Author.");
        }
        return ResponseEntity.ok().body("Author created");
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<Author> authors = repo.findAll();

        List<AuthorDTO> dtos = authors.stream().map(author -> {
            List<BookDTO> bookDTOs = author.getBooks().stream()
                    .map(book -> new BookDTO(
                            book.getTitle(),
                            book.getGenre()
                    ))
                    .toList();

            return new AuthorDTO(
                    author.getFirst_name(),
                    author.getLast_name(),
                    author.getEmail(),
                    author.isAlive(),
                    bookDTOs
            );
        }).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnAuthor(@PathVariable int id) {
        Optional<Author> author = repo.findById(id);
        if (author.isPresent()) {
            return ResponseEntity.ok(author.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No authors with that id were found.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnAuthor(@PathVariable int id, @RequestBody Author body) {
        if (body == null ||
                body.getFirst_name() == null ||
                body.getFirst_name().trim().isEmpty() ||
                body.getLast_name() == null ||
                body.getLast_name().trim().isEmpty() ||
                body.getEmail() == null ||
                body.getEmail().trim().isEmpty()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("All fields must be provided and non-empty.");
        }

        Optional<Author> optionalAuthor = repo.findById(id);
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            author.setFirst_name(body.getFirst_name());
            author.setLast_name(body.getLast_name());
            author.setEmail(body.getEmail());
            author.setAlive(body.isAlive());

            repo.save(author);
            return ResponseEntity.status(HttpStatus.CREATED).body("Author updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No authors with that id were found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable int id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No authors with that id were found.");
        }

        repo.deleteById(id);
        return ResponseEntity.ok().body("Author deleted.");
    }
}
