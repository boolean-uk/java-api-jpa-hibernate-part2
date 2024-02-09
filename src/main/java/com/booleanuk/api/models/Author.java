package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String first_name;
    @Column
    private String last_name;
    @Column
    private String email;
    @Column
    private Boolean alive;

    @OneToMany(mappedBy = "author_id")
    @JsonIgnoreProperties("author_id")
    private List<Book> books;

    public Author(final Integer id) {
        this.id = id;
    }

    public Author(final Integer id, final String firstName, final String lastName, final String email, final Boolean isAlive) {
        this.id = id;
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        this.alive = isAlive;
    }

    public Author(final String firstName, final String lastName, final String email, final Boolean isAlive) {
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        this.alive = isAlive;
    }
}
