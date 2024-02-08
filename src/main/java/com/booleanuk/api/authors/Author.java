package com.booleanuk.api.authors;

import com.booleanuk.api.books.Book;
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

    public Author(String firstName, String lastName, String email, boolean alive){
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setAlive(alive);
    }

    public Author(int id){
        setId(id);
    }
}
