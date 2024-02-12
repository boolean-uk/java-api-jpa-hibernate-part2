package com.booleanuk.api.model;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "publisher")
    @JsonIgnoreProperties("publisher")
    private List<Book> books;

    public Publisher(String name, String location, List<Book> books) {
        this.name = name;
        this.location = location;
        this.books = books;
    }

    public Publisher(int id) {
        this.id = id;
    }
}