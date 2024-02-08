package com.booleanuk.api.model;


import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String genre;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "firstName", "lastName", "email", "alive"})
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "name", "location"})
    private Publisher publisher;




    public Book(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }


}
