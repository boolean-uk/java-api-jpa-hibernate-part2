package com.booleanuk.api.publisher;

import com.booleanuk.api.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "publishers")
@Getter
@Setter
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"publisher"})
    private List<Book> books;

    public Publisher() {
    }

    public Publisher(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
}
