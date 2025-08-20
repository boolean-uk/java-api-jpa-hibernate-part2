package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.booleanuk.api.models.Author;


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
    private String firstName;

    @Column
    private String lastName;

    @ManyToOne
    @JoinColumn(name="author_id", nullable = false)
    @JsonIncludeProperties(value={"name","location"})
    private Author author;


    public Book(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


}
