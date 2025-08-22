package com.booleanuk.api.publishers;

import com.booleanuk.api.books.Book;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column String name;
    @Column String location;

    @OneToMany(mappedBy = "publisher")
    @JsonIncludeProperties({"title", "genre", "author"})
    private List<Book> books;

    public Publisher(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Publisher(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Publisher(int id) {
        this.id = id;
    }

    public Publisher() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
