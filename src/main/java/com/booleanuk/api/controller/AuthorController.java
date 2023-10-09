package com.booleanuk.api.controller;

import com.booleanuk.api.dtos.*;
import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.AuthorRepository;
import com.booleanuk.api.services.AuthorService;
import com.booleanuk.api.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("authors")
public class AuthorController {
    private final AuthorService authorService;

    private final BookService bookService;
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping
    public List<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthorDTOs();
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorWithBooksDTO> getAuthor(@PathVariable Long authorId) {
        Author author = new Author();
        author = this.authorRepository.findById(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found."));

        List<Book> booksByAuthor = bookService.findBooksByAuthor(author);

        AuthorWithBooksDTO authorWithBooksDTO = mapToAuthorWithBooksDTO(author, booksByAuthor);
        return ResponseEntity.ok(authorWithBooksDTO);
    }

    private AuthorWithBooksDTO mapToAuthorWithBooksDTO(Author author, List<Book> booksByAuthor) {
        AuthorWithBooksDTO authorWithBooksDTO = new AuthorWithBooksDTO();
        authorWithBooksDTO.setFirstName(author.getFirstName());
        authorWithBooksDTO.setLastName(author.getLastName());
        authorWithBooksDTO.setEmail(author.getEmail());
        authorWithBooksDTO.setAlive(author.isAlive());

        List<BookWithPublisherDTO> bookWithPublisherDTOS = booksByAuthor.stream()
                .map(this::mapToBookWithPublisherDTO)
                .collect(Collectors.toList());

        authorWithBooksDTO.setBooks(bookWithPublisherDTOS);

        return authorWithBooksDTO;
    }

    private AuthorDTO mapToAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setFirstName(author.getFirstName());
        authorDTO.setLastName(author.getLastName());
        authorDTO.setEmail(author.getEmail());
        authorDTO.setAlive(author.isAlive());
        return authorDTO;
    }

    private BookWithPublisherDTO mapToBookWithPublisherDTO(Book book) {
        BookWithPublisherDTO bookWithPublisherDTO = new BookWithPublisherDTO();
        bookWithPublisherDTO.setTitle(book.getTitle());
        bookWithPublisherDTO.setGenre(book.getGenre());

        Publisher publisher = book.getPublisher();
        if (publisher != null) {
            PublisherDTO publisherDTO = new PublisherDTO();
            publisherDTO.setPublisherId(publisher.getPublisherId());
            publisherDTO.setName(publisher.getName());
            publisherDTO.setLocation(publisher.getLocation());
            bookWithPublisherDTO.setPublisher(publisherDTO);
        }

        return bookWithPublisherDTO;
    }

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody @Valid CreateAuthorDTO createAuthorDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);
        }
        Author author = modelMapper.map(createAuthorDTO, Author.class);
        Author savedAuthor = authorRepository.save(author);
        AuthorDTO authorDTO = modelMapper.map(savedAuthor, AuthorDTO.class);
        return new ResponseEntity<>(authorDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody @Valid UpdateAuthorDTO updateAuthorDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found."));

        existingAuthor.setFirstName(updateAuthorDTO.getFirstName());
        existingAuthor.setLastName(updateAuthorDTO.getLastName());
        existingAuthor.setEmail(updateAuthorDTO.getEmail());
        existingAuthor.setAlive(updateAuthorDTO.getAlive());

        Author updatedAuthor = authorRepository.save(existingAuthor);
        AuthorDTO authorDTO = modelMapper.map(updatedAuthor, AuthorDTO.class);

        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorDTO> deleteAuthor(@PathVariable Long id) {
        Author toDelete = authorService.findById(id);
        if (toDelete == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found.");
        }
        authorService.deleteAuthor(id);

        AuthorDTO deletedAuthorDTO = mapToAuthorDTO(toDelete);
        return ResponseEntity.ok(deletedAuthorDTO);
    }
}
