package com.booleanuk.api.controller;
import com.booleanuk.api.DTO.BookDTO;
import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
import com.booleanuk.api.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.booleanuk.api.model.Book;
import java.util.List;


@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private AuthorRepository authorRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody BookDTO body) {

        Author author = authorRepository.findById(body.getAuthorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Author not found")
                );

        Publisher publisher = publisherRepository.findById(body.getPublisherId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Publisher not found")
                );

        Book book = new Book();
        book.setTitle(body.getTitle());
        book.setGenre(body.getGenre());
        book.setAuthor(author);
        book.setPublisher(publisher);
        return this.bookRepository.save(book);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book updateBook(@PathVariable int id, @RequestBody BookDTO updatedBookDTO) {
        return this.bookRepository.findById(id)
                .map(book -> {
                    Author author = authorRepository.findById(updatedBookDTO.getAuthorId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author not found"));
                    Publisher publisher = publisherRepository.findById(updatedBookDTO.getPublisherId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Publisher not found"));
                    book.setTitle(updatedBookDTO.getTitle());
                    book.setGenre(updatedBookDTO.getGenre());
                    book.setAuthor(author);
                    book.setPublisher(publisher);
                    return this.bookRepository.save(book);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }



    @DeleteMapping("{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        return this.bookRepository.findById(id).map(book -> {
            this.bookRepository.delete(book);
            return ResponseEntity.ok(book);}).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No book with that ID was found"
        ));
    }

}




