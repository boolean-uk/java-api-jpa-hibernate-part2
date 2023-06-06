package com.booleanuk.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "authors")
public class Author {

    //table columns
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "email")
    private String email;

    @Column(name = "alive")
    private boolean alive;


    //constructors
    public Author() {
        super();
    }

    public Author(String first_name, String last_name, String email, boolean alive) {
        super();
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.alive = alive;
    }

    //getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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
