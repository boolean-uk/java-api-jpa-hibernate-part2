package com.booleanuk.api.books;

import com.booleanuk.api.authors.Author;
import com.booleanuk.api.publishers.Publisher;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column String title;
    @Column String genre;

    @ManyToOne
    @JsonIncludeProperties({"id", "firstName", "lastName", "email", "alive"})
    private Author author;

    @ManyToOne
    @JsonIncludeProperties({"id", "name", "location"})
    private Publisher publisher;

    public Book(int id, String title, String genre, Author author) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
    }

    public Book(String title, String genre, Author author) {
        this.title = title;
        this.genre = genre;
        this.author = author;
    }

    public Book(int id) {
        this.id = id;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public int getAuthorId() {
        return author.getId();
    }

    public int getPublisherId() {
        return publisher.getId();
    }
}
