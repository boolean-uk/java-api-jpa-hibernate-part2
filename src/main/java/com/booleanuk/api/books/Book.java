package com.booleanuk.api.books;

import com.booleanuk.api.authors.Author;
import com.booleanuk.api.publishers.Publisher;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String title;
    @Column
    private String genre;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIncludeProperties({"first_name", "last_name", "email", "alive"})
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @JsonIncludeProperties({"name", "location"})
    private Publisher publisher;

    public Book(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    public Book(int id) {
        this.id = id;
    }
}