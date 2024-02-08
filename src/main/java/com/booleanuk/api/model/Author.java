package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name="authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "isAlive",nullable = false)
    private boolean isAlive;

    @OneToMany(mappedBy = "author")
    //For multible values to ignore use "values = {value,value}
    @JsonIgnoreProperties("author")
    private List<Book> books;
    public Author(String firstName, String lastName, String email, boolean isAlive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isAlive = isAlive;
    }
    public Author(int id){
        this.id = id;
    }
}
