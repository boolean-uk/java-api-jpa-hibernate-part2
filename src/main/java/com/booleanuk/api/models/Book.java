package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(insertable = false, updatable = false)
    private int author_id;

    @Column(insertable = false, updatable = false)
    private int publisher_id;

    @ManyToOne
    @JsonIncludeProperties({"first_name", "last_name", "alive"})
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne
    @JsonIncludeProperties({"name", "location"})
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    public Book(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    public Book(int id) {
        this.id = id;
    }
}