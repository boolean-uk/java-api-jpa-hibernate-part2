package com.booleanuk.api.model;

import com.booleanuk.api.repository.AuthorRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String genre;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIncludeProperties(value = {"first_name", "last_name"})
    @JsonIgnoreProperties("books")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @JsonIncludeProperties(value = {"name", "location"})
    @JsonIgnoreProperties("books")
    private Publisher publisher;



    public Book(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    public Book(int id){
        this.id = id;
    }
}
