package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    public String firstName;
    @Column(name = "last_name")
    public String lastName;
    @Column
    public String email;
    @Column(name = "is_alive")
    public Boolean isAlive;

    @OneToMany(mappedBy = "author")
    @JsonIncludeProperties(value = { "first_name", "last_name", "email", "is_alive" })
    private List<Book> books;

    public Author() {}

    public Author(final Integer id) {
        this.id = id;
    }

    public Author(final Integer id, final String firstName, final String lastName, final String email, final Boolean isAlive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAlive = isAlive;
    }

    public Author(final String firstName, final String lastName, final String email, final Boolean isAlive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAlive = isAlive;
    }

    public Integer getId() {
        return id;
    }
}
