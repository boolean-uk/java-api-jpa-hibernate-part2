package com.booleanuk.api.controller;

import com.booleanuk.api.model.ModelDtos.*;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private PublisherRepository repo;

    @PostMapping
    public ResponseEntity<?> createPublisher(@RequestBody Publisher publisher) {
        try {
            Publisher savedPublisher = repo.save(publisher);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not create Publisher.");
        }
        return ResponseEntity.ok().body("Publisher created");
    }

    @GetMapping
    public ResponseEntity<?> getAllPublishers() {
        List<Publisher> publisherList = repo.findAll();

        List<PublisherDTO> dtos = publisherList.stream().map(publisher -> {
            List<BookDTO> bookDTOS = publisher.getBooks().stream().map(book ->
                new BookDTO(book.getTitle(), book.getGenre())).toList();

            return new PublisherDTO(publisher.getName(), publisher.getLocation(), bookDTOS);
        }).toList();

        return ResponseEntity.ok(dtos);
    }

//    @GetMapping
//    public ResponseEntity<List<Publisher>> getAllPublishers() {
//        return ResponseEntity.ok(repo.findAll());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnPublisher(@PathVariable int id) {
        Optional<Publisher> publisher = repo.findById(id);
        if (publisher.isPresent()) {
            return ResponseEntity.ok(publisher.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No publishers with that id were found.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnPublisher(@PathVariable int id, @RequestBody Publisher body) {
        if (body == null ||
                body.getName() == null ||
                body.getName().trim().isEmpty() ||
                body.getLocation() == null ||
                body.getLocation().trim().isEmpty()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields must be provided and non-empty.");
        }

        Optional<Publisher> optionalPublisher = repo.findById(id);
        if (optionalPublisher.isPresent()) {
            Publisher publisher = optionalPublisher.get();
            publisher.setName(body.getName());
            publisher.setLocation(body.getLocation());

            repo.save(publisher);

            return ResponseEntity.status(HttpStatus.CREATED).body("Publisher updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No publishers with that id were found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable int id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No publishers with that id were found.");
        }

        repo.deleteById(id);
        return ResponseEntity.ok().body("Publisher deleted.");
    }



    //     INSERT DATA TO DB

//    @Autowired
//    private AuthorRepository repoAuthor;
//    @Autowired
//    private BookRepository repoBook;
//
//
//    @GetMapping
//    public void insertData() {
//        Publisher pub1 = new Publisher("Penguin Books", "New York");
//        Publisher pub2 = new Publisher("HarperCollins", "London");
//
//        repo.saveAll(List.of(pub1, pub2));
//
//        // Insert Authors
//        Author author1 = new Author("John", "Doe", "john@example.com", true);
//        Author author2 = new Author("Jane", "Smith", "jane@example.com", false);
//        Author author3 = new Author("Emily", "Clark", "emily@example.com", true);
//        Author author4 = new Author("Michael", "Johnson", "michaelj@example.com", true);
//        Author author5 = new Author("Sarah", "Williams", "sarahw@example.com", false);
//        Author author6 = new Author("David", "Brown", "davidb@example.com", true);
//
//        repoAuthor.saveAll(List.of(author1, author2, author3, author4, author5, author6));
//
//        // Insert Books
//        List<Book> books = List.of(
//                new Book("The Great Adventure", "Fiction", author1, pub1),
//                new Book("The Great Return", "Fiction", author1, pub2),
//                new Book("Deep Space", "Sci-Fi", author2, pub2),
//                new Book("Mystery Night", "Mystery", author3, pub1),
//                new Book("The Forgotten Clue", "Mystery", author4, pub1),
//                new Book("The Silent Forest", "Fantasy", author4, pub2),
//                new Book("Broken Code", "Tech Thriller", author1, pub1),
//                new Book("Zero Day", "Tech Thriller", author5, pub2),
//                new Book("Shadows of Time", "Historical", author6, pub1),
//                new Book("Echoes of the Past", "Historical", author6, pub1)
//        );
//        repoBook.saveAll(books);
//    }
}
