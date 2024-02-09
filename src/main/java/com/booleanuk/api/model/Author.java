package com.booleanuk.api.model;

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

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "email")
    private String email;

    @Column(name = "alive")
    private boolean alive;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    public Author( String firstName, String lastName, String email, boolean alive) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        this.alive = alive;
    }

    public Author(int id) {
        this.id = id;
    }
}
