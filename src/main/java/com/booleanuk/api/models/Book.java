package com.booleanuk.api.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;
    @Column(name = "title")
    private String title;
    @Column(name = "genre")
    private String genre;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author_id;
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher_id;

    public Book(int book_id, String title, String genre, Author author, Publisher publisher) {
        this.book_id = book_id;
        this.title = title;
        this.genre = genre;
        this.author_id = author;
        this.publisher_id = publisher;
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

    public Author getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Author author_id) {
        this.author_id = author_id;
    }

    public Publisher getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(Publisher publisher_id) {
        this.publisher_id = publisher_id;
    }
}
