package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Integer id;

    private String name;
    private String location;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    @JsonIncludeProperties(value = {"title", "genre"})
    @JsonIgnoreProperties(value = {"id", "publisher"})
    private List<Book> books;

    public Publisher(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Publisher (int id){
        this.id=id;
    }
}
