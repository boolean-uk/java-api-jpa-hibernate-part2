package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String LastName;
    @Column(name = "email")
    private String email;
    @Column(name = "alive")
    private boolean alive;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties("author")
    private List<Book> books;

    public Author(String firstName, String lastName, String email, boolean alive) {
        this.firstName = firstName;
        this.LastName = lastName;
        this.email = email;
        this.alive = alive;
    }

    public Author(int id) {
        this.id = id;
    }
}
