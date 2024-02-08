package com.booleanuk.api.model;

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
    private String first_name;

    @Column
    private String last_name;

    @Column
    private String email;

    @Column
    private boolean alive;

    public Author(String first_name, String last_name, String email, boolean alive) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.alive = alive;
    }

}
