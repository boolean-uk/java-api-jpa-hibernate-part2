package com.booleanuk.api.model.dto;

import com.booleanuk.api.model.jpa.Author;
import com.booleanuk.api.model.jpa.Book;
import com.booleanuk.api.model.jpa.Publisher;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.util.List;

public class BookListDto {

    @Data
    public static class BookDto {
        private int id;

        private String title;
        private String genre;
        private String publisher;
        private String author;

        public BookDto(Book book) {
            setId(book.getId());
            setTitle(book.getTitle());
            setGenre(book.getGenre());
            setPublisher(book.getPublisher().getName());
            setAuthor(book.getAuthor().getFirstName().concat(" ").concat(book.getAuthor().getLastName()));
        }
    }

    @Data
    public static class AuthorDto {
        private int id;

        private String firstName;
        private String lastName;
        private String email;
        private boolean alive;

        private List<BookDto> books;

        public AuthorDto(Author author) {
            setId(author.getId());
            setFirstName(author.getFirstName());
            setLastName(author.getLastName());
            setEmail(author.getEmail());
            setAlive(author.isAlive());
            setBooks(author.getBooks().stream().map(BookDto::new).toList());
        }
    }

    @Data
    public static class PublisherDto {
        private int id;

        private String name;
        private String location;

        private List<BookDto> books;

        public PublisherDto(Publisher publisher) {
            setId(publisher.getId());
            setName(publisher.getName());
            setLocation(publisher.getLocation());
            setBooks(publisher.getBooks().stream().map(BookDto::new).toList());
        }
    }
}
