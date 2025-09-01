package com.booleanuk.api.controller;

import com.booleanuk.api.model.dto.BookDto;
import com.booleanuk.api.model.pojo.Author;
import com.booleanuk.api.model.pojo.Book;
import com.booleanuk.api.model.pojo.Publisher;
import com.booleanuk.api.model.repository.AuthorRepository;
import com.booleanuk.api.model.repository.BookRepository;
import com.booleanuk.api.model.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> result = new ArrayList<>();
        for (Book b : books) {
            result.add(toDto(b));
        }
        return result;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBookById(@PathVariable int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return toDto(book);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody BookDto dto) {
        Author author = authorRepository.findById(dto.author_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid author_id"));
        Publisher publisher = publisherRepository.findById(dto.publisher_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid publisher_id"));

        Book book = new Book(dto.title(), dto.genre(), author, publisher);
        Book saved = bookRepository.save(book);
        return toDto(saved);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto updateBook(@RequestBody BookDto dto, @PathVariable int id) {
        Book toUpdate = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Author author = authorRepository.findById(dto.author_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid author_id"));

        Publisher publisher = publisherRepository.findById(dto.publisher_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid publisher_id"));

        toUpdate.setTitle(dto.title());
        toUpdate.setGenre(dto.genre());
        toUpdate.setAuthor(author);
        toUpdate.setPublisher(publisher);

        Book saved = bookRepository.save(toUpdate);
        return toDto(saved);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto deleteBook(@PathVariable int id) {
        Book toDelete = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        BookDto dto = toDto(toDelete);
        bookRepository.delete(toDelete);
        return dto;
    }

    private BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getGenre(),
                book.getAuthor().getId(),
                book.getPublisher().getId()
        );
    }
}
