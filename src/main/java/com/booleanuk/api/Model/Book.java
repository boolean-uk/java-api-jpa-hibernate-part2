package com.booleanuk.api.Model;

import jakarta.persistence.*;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String genre;

    @ManyToOne
    @JoinColumn(name="author_id", nullable = false)
    private Author author;

    @ManyToOne
    @JoinColumn(name="publisher_id", nullable = false)
    private Publisher publisher;

    public Book(String title, String genre, Author author, Publisher publisher){
        this.title=title;
        this.genre=genre;
        this.author=author;
        this.publisher=publisher;
    }



    public Book(){

    }

    public Integer getId() {return id;}

    public String getTitle() {return title;}

    public String getGenre() {return genre;}

    public void setId(Integer id) {this.id = id;}

    public void setTitle(String title) {this.title = title;}

    public void setGenre(String genre) {this.genre = genre;}

    public Author getAuthor() {
        return author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
