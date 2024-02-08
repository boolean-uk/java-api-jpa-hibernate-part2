package com.booleanuk.api.publisher;

import com.booleanuk.api.book.Book;
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

    @OneToMany(mappedBy = "publisher")
    @JsonIgnoreProperties("publisher")
    //@JsonIncludeProperties(value = {"id", "firstName", "lastName"})
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
