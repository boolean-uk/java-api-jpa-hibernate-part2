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

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    //@JsonBackReference
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    //@JsonBackReference
    private Publisher publisher;


    //constructors
    public Book() {
        super();
    }

    public Book(String title, String genre) {
        super();
        this.title = title;
        this.genre = genre;
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

    public Author getAuthor(Author author) {
        return this.author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher(Publisher publisher) {
        return this.publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
