package com.booleanuk.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    //table columns
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "author_id")
    private Integer author_id;

    @Column(name = "publisher_id")
    private Integer publisher_id;


    //constructors
    public Book() {
        super();
    }

    public Book(String title, String genre, Integer author_id, Integer publisher_id) {
        super();
        this.title = title;
        this.genre = genre;
        this.author_id = author_id;
        this.publisher_id = publisher_id;
    }

    //getters and setters

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

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public Integer getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(Integer publisher_id) {
        this.publisher_id = publisher_id;
    }
}