package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "genre",nullable = false)
    private String genre;

    @ManyToOne
    @JoinColumn(name = "author_id",nullable = false)
    @JsonIncludeProperties(value ={"firstName","lastName","email","isAlive"})
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id",nullable = false)
    @JsonIncludeProperties(value ={"name","location"})
    private Publisher publisher;

    public Book(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }
}
