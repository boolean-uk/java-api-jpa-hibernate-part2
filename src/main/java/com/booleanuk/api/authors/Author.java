package com.booleanuk.api.authors;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Author(String firstName, String lastName, String email, boolean alive){
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setAlive(alive);
    }
}
