package com.booleanuk.api.books;


import com.booleanuk.api.authors.Author;
import com.booleanuk.api.publishers.Publisher;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties({"id","books"})
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    @JsonIgnoreProperties({"id","books"})
    private Publisher publisher;

    public Book(int id) {
        this.id = id;
    }

}
