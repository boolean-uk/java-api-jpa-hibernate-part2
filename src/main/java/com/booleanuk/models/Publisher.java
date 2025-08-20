package com.booleanuk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column
    private String name;

    @Column
    private String location;

    // can add parts that are not columns or part of the table
    @OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"title", "genre"}) // to not get infinite loop of relation
    private List<Book> books;

    public Publisher(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
