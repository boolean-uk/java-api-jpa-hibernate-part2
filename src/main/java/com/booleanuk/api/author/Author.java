package com.booleanuk.api.author;


import com.booleanuk.api.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private boolean alive;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties("author")

    private List<Book> books;

    public Author(String firstName, String lastName, String email, boolean alive, List<Book> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.alive = alive;
        this.books = books;
    }

    public Author(int id) {
        this.id = id;
    }
}