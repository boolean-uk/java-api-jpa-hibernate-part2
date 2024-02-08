package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    public String name;
    @Column(name = "location")
    public String location;

    @OneToMany(mappedBy = "publisher")
    @JsonIgnoreProperties("publisher")
    private List<Book> books;

    public Publisher() {}

    public Publisher(final Integer id) {
        this.id = id;
    }

    public Publisher(final Integer id, final String name, final String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Publisher(final String name, final String location) {
        this.name = name;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }
}
