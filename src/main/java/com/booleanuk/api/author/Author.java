package com.booleanuk.api.author;

import com.booleanuk.api.book.Book;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column
    private boolean isAlive;

//    @OneToMany(mappedBy = "author")
//    @JsonIncludeProperties(value =  {"id", "firstName", "lastName", "email", "isAlive"})
//    private List<Book> books;
//

    public Author(String firstName, String lastName, String email, boolean isAlive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAlive = isAlive;
    }
}
