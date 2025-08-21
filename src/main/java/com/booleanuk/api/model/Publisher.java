package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "publishers")
@Data
@NoArgsConstructor
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank
    private String name;

    @Column
    private String location;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"publisher_id"})
    private List<Book> books;

    public Publisher(int id) {
        this.id = id;
    }

    public Publisher(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
