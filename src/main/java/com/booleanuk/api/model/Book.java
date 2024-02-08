package com.booleanuk.api.model;

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

    @Column
    private int author_id;

    @Column
    private int publisher_id;

    public Book(String title, String genre, int author_id, int publisher_id) {
        this.title = title;
        this.genre = genre;
        this.author_id = author_id;
        this.publisher_id = publisher_id;
    }
}
