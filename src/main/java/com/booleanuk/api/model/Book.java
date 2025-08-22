package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;
    @Column(name = "genre")
    private String genre;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonBackReference(value = "author-books")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @JsonBackReference(value = "publisher-books")
    private Publisher publisher;

    public Book(String title, String genre, Author author, Publisher publisher) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
    }

    public Book() {}

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

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }



}
