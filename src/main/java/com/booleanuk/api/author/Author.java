package com.booleanuk.api.author;

import com.booleanuk.api.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @Column(name = "is_alive")
    private boolean isAlive;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties({"author", "id"})
    private List<Book> books;

    public Author(String firstName, String lastName, String email, boolean isAlive){
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        this.isAlive = isAlive;
    }

    public Author(int id){
        this.id = id;
    }

}
