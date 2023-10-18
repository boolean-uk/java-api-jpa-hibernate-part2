package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "alive")
    private boolean isAlive;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties(value = {"author"})
    private List<Book> books;


    public Author(){
        super();
    }

    public Author(int id) {
        this.id = id;
    }

    public Author(String firstName, String lastName, String email, boolean isAlive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAlive = isAlive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public List<Book> getBooks() {return this.books;}

    public void setBooks(List<Book> books) { this.books = books;}
}
