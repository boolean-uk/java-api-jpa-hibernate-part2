package com.booleanuk.api.publishers;

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
@RequestMapping("publishers")
public class PublisherController {
  private final PublisherRepository repository;

  public PublisherController(PublisherRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public ResponseEntity<List<Publisher>> get() {
    return ResponseEntity.ok(this.repository.findAll());
  }

  @GetMapping(value = "{id}")
  public ResponseEntity<Publisher> get(@PathVariable int id) throws ResponseStatusException {
    return this.repository.findById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with id '" + id + "' exists"));
  }

  @PostMapping
  public ResponseEntity<Publisher> post(@RequestBody Publisher publisher) throws ResponseStatusException {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(publisher));
    } catch (DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create publisher");
    }
  }

  @PutMapping(value = "{id}")
  public ResponseEntity<Publisher> put(@PathVariable int id, @RequestBody Publisher publisher)
      throws ResponseStatusException {
    Optional<Publisher> maybePublisherToUpdate = this.repository.findById(id);
    if (maybePublisherToUpdate.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with id '" + id + "' exists");

    Publisher publisherToUpdate = maybePublisherToUpdate.get();
    publisherToUpdate.setName(publisher.getName());
    publisherToUpdate.setLocation(publisher.getLocation());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(this.repository.save(publisherToUpdate));
  }

  @DeleteMapping(value = "{id}")
  public ResponseEntity<Publisher> delete(@PathVariable int id) throws ResponseStatusException {
    Optional<Publisher> maybePublisherToDelete = this.repository.findById(id);
    if (maybePublisherToDelete.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No publisher with id '" + id + "' exists");

    this.repository.deleteById(id);
    return ResponseEntity.ok(maybePublisherToDelete.get());
  }
}
