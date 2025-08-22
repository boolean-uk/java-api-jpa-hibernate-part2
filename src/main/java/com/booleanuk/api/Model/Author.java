package com.booleanuk.api.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Getter
    private int id;
    @Setter
    @Getter
    @Column(name = "first_name")
    private String first_name;
    @Setter
    @Getter
    @Column(name = "last_name")
    private String last_name;
    @Setter
    @Getter
    @Column(name = "email")
    private String email;
    @Setter
    @Getter
    @Column(name = "alive")
    private boolean alive;

    public Author() {
    }

    public Author(String first_name, String last_name, String email, boolean alive) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.alive = alive;



    }



}

