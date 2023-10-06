package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String genre;
    @ManyToOne
    @JoinColumn(name = "authorId", referencedColumnName = "authorId", nullable = false)
    private Author author;
    @ManyToOne
    @JoinColumn(name = "publisherId", referencedColumnName = "publisherId", nullable = false)
    private Publisher publisher;

    public Book(String title, String genre, Author author, Publisher publisher) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
    }
}
