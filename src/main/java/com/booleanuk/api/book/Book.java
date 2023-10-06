package com.booleanuk.api.book;

import com.booleanuk.api.author.Author;
import com.booleanuk.api.publisher.Publisher;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties({"books"})
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @JsonIgnoreProperties({"books"})
    private Publisher publisher;

    public Book() {
    }

    public Book(int id, String title, String genre, Author author, Publisher publisher) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
    }
}
