package com.booleanuk.api.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "books_id")
    private Integer id;

    @Column(name = "title")
    private String title;
    @Column(name = "genre")
    private String genre;

    @Column(name = "author_id")
    private int authorID;
    @Column(name = "publisher_id")
    private int publisherID;

    public Book() {

    }
//    @ManyToOne
//    @JoinColumn(name = "publisher_id")
//
//    private Publisher publisher;
//
//    @ManyToOne
//    @JoinColumn(name = "author_id")
//
//    private Author author;



    public Book(String title, String genre, int authorID, int publisherID) {
        this.title = title;
        this.genre = genre;
        this.authorID = authorID;
        this.publisherID = publisherID;
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

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public int getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
    }
}
