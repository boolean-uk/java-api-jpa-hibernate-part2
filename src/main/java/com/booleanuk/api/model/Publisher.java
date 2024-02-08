package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String location;

    // Can show all books from a publisher if needed
    /*@OneToMany(mappedBy = "publisher")
    @JsonIgnoreProperties(value = {"publisher"})
    private List<Book> books;*/
}
