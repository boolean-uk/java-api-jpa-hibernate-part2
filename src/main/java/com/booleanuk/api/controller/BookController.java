package com.booleanuk.api.controller;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.ModelDtos.BookDTO;
import com.booleanuk.api.model.ModelDtos.AuthorDTO;
import com.booleanuk.api.model.ModelDtos.PublisherDTO;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository repo;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookDTO dto) {

        Optional<Author> authorOpt = authorRepository.findById(dto.getAuthor_id());
        Optional<Publisher> publisherOpt = publisherRepository.findById(dto.getPublisher_id());

        if (authorOpt.isEmpty() || publisherOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Author or Publisher not found.");
        }

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setGenre(dto.getGenre());
        book.setAuthor(authorOpt.get());
        book.setPublisher(publisherOpt.get());

        repo.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body("Book created.");
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<Book> books = repo.findAll();

        List<BookDTO> dtos = books.stream().map(book -> {
            AuthorDTO authorDTO = new AuthorDTO(
                    book.getAuthor().getFirst_name(),
                    book.getAuthor().getLast_name(),
                    book.getAuthor().getEmail(),
                    book.getAuthor().isAlive()
            );

            PublisherDTO publisherDTO = new PublisherDTO(
                    book.getPublisher().getName(),
                    book.getPublisher().getLocation()
            );

            return new BookDTO(
                    book.getTitle(),
                    book.getGenre(),
                    authorDTO,
                    publisherDTO
            );
        }).toList();

        return ResponseEntity.ok(dtos);
    }

//    @GetMapping
//    public ResponseEntity<List<Book>> getAllBooks() {
//        return ResponseEntity.ok(repo.findAll());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnBook(@PathVariable int id) {
        Optional<Book> book = repo.findById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books with that id were found.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnBook(@PathVariable int id, @RequestBody Book body) {
        if (body == null ||
                body.getGenre() == null || body.getGenre().trim().isEmpty() ||
                body.getTitle() == null || body.getTitle().trim().isEmpty() ||
                body.getAuthor() == null ||
                body.getPublisher() == null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields must be provided and non-empty.");
        }

        Optional<Book> optionalBook = repo.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(body.getTitle());
            book.setGenre(body.getGenre());
            book.setAuthor(body.getAuthor());
            book.setPublisher(body.getPublisher());

            repo.save(book);

            return ResponseEntity.status(HttpStatus.CREATED).body("Book updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books with that id were found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books with that id were found.");
        }

        repo.deleteById(id);
        return ResponseEntity.ok().body("Book deleted.");
    }

}
