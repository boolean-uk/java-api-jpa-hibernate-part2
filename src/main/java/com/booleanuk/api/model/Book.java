package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;
    @Column
    private String genre;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "first_name", "last_name", "email", "alive"})
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "name", "location"})
    private Publisher publisher;

    public Book(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }
    public Book(){}
    public Book(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

}