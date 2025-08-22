package com.booleanuk.books;

import com.booleanuk.publishers.Publisher;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.booleanuk.authors.Author;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties({ "id", "books" })
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @JsonIgnoreProperties({ "books", "id" })
    private Publisher publisher;

    public Book(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }
}
