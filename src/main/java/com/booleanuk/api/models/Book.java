package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;
    @Column
    private String genre;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIncludeProperties(value = { "first_name", "last_name", "email", "alive" })
    private Author author_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIncludeProperties(value = { "name", "location" })
    private Publisher publisher_id;

    public Book(final Integer id) {
        this.id = id;
    }

    public Book(final String title, final String genre, final Author author, final Publisher publisher) {
        this.title = title;
        this.genre = genre;
        this.author_id = author;
        this.publisher_id = publisher;
    }
}
