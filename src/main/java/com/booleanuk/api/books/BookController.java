package com.booleanuk.api.books;

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
@RequestMapping("books")
public class BookController {
  private final BookRepository repository;

  public BookController(BookRepository repository) {
    this.repository = repository;
  }

  @PostMapping
  public ResponseEntity<Book> post(@RequestBody Book book) throws ResponseStatusException {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(book));
    } catch (DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create book " + e.getMessage());
    }
  }

  @GetMapping
  public ResponseEntity<List<Book>> get() {
    return ResponseEntity.ok(this.repository.findAll());
  }

  @GetMapping(value = "{id}")
  public ResponseEntity<Book> get(@PathVariable int id) throws ResponseStatusException {
    return this.repository.findById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with id '" + id + "' exists"));
  }

  @PutMapping(value = "{id}")
  public ResponseEntity<Book> put(@PathVariable int id, @RequestBody Book book) throws ResponseStatusException {
    Optional<Book> maybeBookToUpdate = this.repository.findById(id);
    if (maybeBookToUpdate.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with id '" + id + "' exists");

    Book bookToUpdate = maybeBookToUpdate.get();
    bookToUpdate.setTitle(book.getTitle());
    bookToUpdate.setGenre(book.getGenre());
    bookToUpdate.setAuthorId(book.getAuthorId());
    bookToUpdate.setPublisherId(book.getPublisherId());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.repository.save(bookToUpdate));
  }

  @DeleteMapping(value = "{id}")
  public ResponseEntity<Book> delete(@PathVariable int id) throws ResponseStatusException {
    Optional<Book> maybeBookToDelete = this.repository.findById(id);
    if (maybeBookToDelete.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with id '" + id + "' exists");

    this.repository.deleteById(id);
    return ResponseEntity.ok(maybeBookToDelete.get());
  }
}
