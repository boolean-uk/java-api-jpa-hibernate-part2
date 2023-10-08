package com.booleanuk.api.controller;

import com.booleanuk.api.dtos.BookDTO;
import com.booleanuk.api.dtos.CreateBookDTO;
import com.booleanuk.api.dtos.UpdateBookDTO;
import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.repository.BookRepository;
import com.booleanuk.api.repository.PublisherRepository;
import com.booleanuk.api.services.AuthorService;
import com.booleanuk.api.services.BookService;
import com.booleanuk.api.services.PublisherService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    public BookController(BookService bookService,
                          ModelMapper modelMapper,
                          AuthorRepository authorRepository,
                          PublisherRepository publisherRepository,
                          AuthorService authorService,
                          PublisherService publisherService) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return books.stream()
                .map(this::mapToBookDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        BookDTO bookDTO = mapToBookDTO(book);
        return ResponseEntity.ok(bookDTO);
    }


    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody @Valid CreateBookDTO createBookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        Book book = modelMapper.map(createBookDTO, Book.class);

        Long authorId = createBookDTO.getAuthorId();
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found."));
        book.setAuthor(author);

        Long publisherId = createBookDTO.getPublisherId();
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found."));
        book.setPublisher(publisher);

        Book createdBook = bookService.createBook(book);

        BookDTO createdBookDTO = modelMapper.map(createdBook, BookDTO.class);

        return new ResponseEntity<>(createdBookDTO, HttpStatus.CREATED);
    }
    private BookDTO mapToBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid UpdateBookDTO updateBookDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        Book existingBook = bookService.findById(id);
        if (existingBook == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found.");
        }

        existingBook.setTitle(updateBookDTO.getTitle());
        existingBook.setGenre(updateBookDTO.getGenre());

        Author author = authorService.findById(updateBookDTO.getAuthorId());
        Publisher publisher = publisherService.findById(updateBookDTO.getPublisherId());

        existingBook.setAuthor(author);
        existingBook.setPublisher(publisher);

        Book updatedBook = bookService.updateBook(existingBook);
        BookDTO bookDTO = mapToBookDTO(updatedBook);
        return ResponseEntity.ok(bookDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable Long id) {
        Book toDelete = this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found."));

        this.bookRepository.delete(toDelete);
        BookDTO bookDTO = mapToBookDTO(toDelete);
        return ResponseEntity.ok(bookDTO);
    }
}
