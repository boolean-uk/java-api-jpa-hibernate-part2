package com.booleanuk.api.model.controller;

import com.booleanuk.api.model.jpa.Author;
import com.booleanuk.api.model.jpa.Book;
import com.booleanuk.api.model.jpa.Publisher;
import com.booleanuk.api.model.repository.AuthorRepository;
import com.booleanuk.api.model.repository.BookRepository;
import com.booleanuk.api.model.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("utils")
public class UtilsController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @RequestMapping("initialize")
    public String initializeDatabase() {
        // Clear existing data
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        publisherRepository.deleteAll();

        // Initialize sample data
        // Publishers
        Publisher publisher1 = new Publisher("John Wiley & Sons", "111 River St, Hoboken, NJ 07030");
        Publisher publisher2 = new Publisher("Penguin Random House", "1745 Broadway, New York, NY 10019");
        Publisher publisher3 = new Publisher("HarperCollins", "195 Broadway, New York, NY 10007");

        List.of(publisher1, publisher2, publisher3).forEach(publisherRepository::save);

        // Authors
        Author author1 = new Author("John", "Doe", "john@doe.com", true);
        Author author2 = new Author("Jane", "Smith", "jane@smith.com", true);
        Author author3 = new Author("Emily", "Johnson", "emily@smith.com", false);

        List.of(author1, author2, author3).forEach(authorRepository::save);

        // Books
        Book book1 = new Book("Java Programming", "Java", author1, publisher1);
        Book book2 = new Book("Spring Boot in Action", "Java", author2, publisher2);
        Book book3 = new Book("Learning Python", "Python", author3, publisher3);
        Book book4 = new Book("Effective Java", "Java", author1, publisher1);
        Book book5 = new Book("Clean Code", "Programming", author2, publisher2);

        List.of(book1, book2, book3, book4, book5).forEach(bookRepository::save);

        return "Database initialized with sample data.";
    }
}
