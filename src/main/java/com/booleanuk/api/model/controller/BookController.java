package com.booleanuk.api.model.controller;

import com.booleanuk.api.model.dto.BookListDto;
import com.booleanuk.api.model.dto.BookRequestDto;
import com.booleanuk.api.model.jpa.Book;
import com.booleanuk.api.model.repository.AuthorRepository;
import com.booleanuk.api.model.repository.BookRepository;
import com.booleanuk.api.model.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public ResponseEntity<List<BookListDto.BookDto>> getAll() {
        return ResponseEntity.ok(bookRepository.findAll().stream()
                .map(BookListDto.BookDto::new).toList()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            BookListDto.BookDto bookDto = new BookListDto.BookDto(bookRepository.findById(id).orElseThrow());
            return ResponseEntity.ok(bookDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No book with that id was found.");
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody BookRequestDto bookRequestDto) {
        try {
            Book book = new Book(bookRequestDto);
            book.setAuthor(authorRepository.findById(bookRequestDto.getAuthorId()).orElseThrow());
            book.setPublisher(publisherRepository.findById(bookRequestDto.getPublisherId()).orElseThrow());
            bookRepository.save(book);

            BookListDto.BookDto bookResponse = new BookListDto.BookDto(book);

            return ResponseEntity.status(HttpStatus.CREATED).body(bookResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Could not create book, please check all required fields are correct.");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody BookRequestDto bookRequestDto) {
        try {
            Book book = bookRepository.findById(id).orElseThrow();
            book.setTitle(bookRequestDto.getTitle());
            book.setGenre(bookRequestDto.getGenre());
            System.out.println("Book found and title/genre set");
            System.out.println(authorRepository.findById(bookRequestDto.getAuthorId()).isPresent());
            book.setAuthor(authorRepository.findById(bookRequestDto.getAuthorId()).orElseThrow());
            System.out.println("author found and saved");
            book.setPublisher(publisherRepository.findById(bookRequestDto.getPublisherId()).orElseThrow());

            bookRepository.save(book);
            BookListDto.BookDto bookResponse = new BookListDto.BookDto(bookRepository.save(book));
            return ResponseEntity.ok(bookResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not update book, please check all required fields are correct.");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            Book book = bookRepository.findById(id).orElseThrow();
            bookRepository.deleteById(id);
            BookListDto.BookDto bookResponse = new BookListDto.BookDto(book);
            return ResponseEntity.ok(bookResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not delete book, please check all required fields are correct.");
        }
    }
}
