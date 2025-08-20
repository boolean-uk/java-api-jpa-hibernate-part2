package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    @Column(insertable = false, updatable = false)
    private int author_id;

    @Column(insertable = false, updatable = false)
    private int publisher_id;


    @ManyToOne
    @JsonIncludeProperties(value = {"firstName", "lastName", "alive"})
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;


    @ManyToOne
    @JsonIncludeProperties(value = {"name","location"})
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;


}
