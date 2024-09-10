package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@EqualsAndHashCode(exclude = "books")
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String first_name;
    private String last_name;
    private String email;
    private Boolean alive;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIncludeProperties(value = {"title", "genre"})
    @JsonIgnoreProperties(value = {"id", "author"})
    private List<Book> books;

    public Author(String first_name, String last_name, String email, Boolean alive) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.alive = alive;
    }

    public Author(int id){
        this.id = id;
    }
}
