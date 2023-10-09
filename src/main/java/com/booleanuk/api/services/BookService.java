package com.booleanuk.api.services;

import com.booleanuk.api.model.Author;
import com.booleanuk.api.model.Book;
import com.booleanuk.api.model.Publisher;
import com.booleanuk.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findBooksByAuthor(Author author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> findBooksByPublisher(Publisher publisher) {
        return bookRepository.findByPublisher(publisher);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found."));
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
}