package com.booleanuk.api.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
    @JoinColumn(name = "author_id",nullable = false)
    @JsonIgnoreProperties("books")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id",nullable = false)
    @JsonIgnoreProperties("books")
    private Publisher publisher;

    public Book(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

}
