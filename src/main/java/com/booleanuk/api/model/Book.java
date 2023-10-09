package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;
    @Column
    @NotBlank
    private String title;

    @Column
    @NotBlank
    private String genre;

    @JsonProperty("author_id")
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIgnoreProperties("authorId")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @JsonIgnoreProperties("publisherId")
    private Publisher publisher;


}
