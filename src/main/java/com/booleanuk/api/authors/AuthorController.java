package com.booleanuk.api.authors;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("authors")
public class AuthorController {
  private final AuthorRepository repository;

  public AuthorController(AuthorRepository repository) {
    this.repository = repository;
  }

  @PostMapping
  public ResponseEntity<Author> post(@RequestBody Author author) throws ResponseStatusException {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(author));
    } catch (DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create author");
    }
  }

  @GetMapping
  public ResponseEntity<List<Author>> get() {
    return ResponseEntity.ok(
        this.repository.findAll().stream()
            .sorted((author1, author2) -> author1.getId() - author2.getId())
            .toList());
  }

  @GetMapping(value = "{id}")
  public ResponseEntity<Author> get(@PathVariable int id) throws ResponseStatusException {
    return this.repository.findById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with id '" + id + "' exists"));
  }

  @PutMapping(value = "{id}")
  public ResponseEntity<Author> put(@PathVariable int id, @RequestBody Author author) throws ResponseStatusException {
    Optional<Author> maybeAuthorToUpdate = this.repository.findById(id);
    if (maybeAuthorToUpdate.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with id '" + id + "' exists");

    Author authorToUpdate = maybeAuthorToUpdate.get();
    authorToUpdate.setFirstName(author.getFirstName());
    authorToUpdate.setLastName(author.getLastName());
    authorToUpdate.setEmail(author.getEmail());
    authorToUpdate.setAlive(author.getAlive());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.repository.save(authorToUpdate));
  }

  @DeleteMapping(value = "{id}")
  public ResponseEntity<Author> delete(@PathVariable int id) throws ResponseStatusException {
    Optional<Author> maybeAuthorToDelete = this.repository.findById(id);
    if (maybeAuthorToDelete.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with id '" + id + "' exists");

    this.repository.deleteById(id);
    return ResponseEntity.ok(maybeAuthorToDelete.get());
  }
}
