package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
//import com.booleanuk.models.Book;


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
    private Boolean alive;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties({"author"})
    private List<Book> books;


    public Author(String firstName, String lastName, String email, Boolean alive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.alive = alive;
    }
}
