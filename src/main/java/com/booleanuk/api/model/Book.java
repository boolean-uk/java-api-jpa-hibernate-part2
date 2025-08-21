package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String genre;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonProperty("author_id")
    @JsonIgnoreProperties({"books"})
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @JsonProperty("publisher_id")
    @JsonIgnoreProperties({"books"})
    private Publisher publisher;

    public Book(int id) {
        this.id = id;
    }

    public Book(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    public Book(String title, String genre, Author author, Publisher publisher) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
    }
}
