package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @Column
    public String title;
    @Column
    public String genre;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIncludeProperties(value = { "first_name", "last_name", "email", "is_alive" })
    public Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @JsonIncludeProperties(value = { "name", "location" })
    public Publisher publisher;

    public Book() {}

    public Book(final Integer id) {
        this.id = id;
    }

    public Book(final Integer id, final String title, final String genre, final Author author, final Publisher publisher) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
    }

    public Book(final String title, final String genre, final Author author, final Publisher publisher) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
    }

    public Integer getId() {
        return id;
    }
}
