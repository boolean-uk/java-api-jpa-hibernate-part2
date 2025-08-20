package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
//import com.booleanuk.models.Book;


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
    private String name;

    @Column
    private String location;

//    @OneToMany(mappedBy = "department")
//    @JsonIgnoreProperties({"department"})
//    private List<Book> employees;

    public Author(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
