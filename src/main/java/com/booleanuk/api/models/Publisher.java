package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;
    @Column
    private String location;

    @OneToMany(mappedBy = "publisher_id")
    @JsonIgnoreProperties("publisher_id")
    private List<Book> books;

    public Publisher(final Integer id) {
        this.id = id;
    }

    public Publisher(final Integer id, final String name, final String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Publisher(final String name, final String location) {
        this.name = name;
        this.location = location;
    }
}
