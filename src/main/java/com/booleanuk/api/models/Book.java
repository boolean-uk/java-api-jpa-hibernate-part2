package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "genre")
    private String genre;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;
}
