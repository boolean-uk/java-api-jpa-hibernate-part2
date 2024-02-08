package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String genre;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "firstName", "lastName", "email", "alive"})
    private Author author;

    @OneToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "name", "location"})
    private Publisher publisher;
}
