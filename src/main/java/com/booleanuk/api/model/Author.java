package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "alive")
    private boolean alive;

    @OneToMany(mappedBy = "author")
    @JsonIncludeProperties("author")
    private ArrayList<Book> books;

    public Author(){
        super();
    }

    public Author(int id) {
        Id = id;
    }

    public Author(String firstName, String lastName, String email, boolean alive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.alive = alive;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
