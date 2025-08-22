package com.booleanuk.api.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "first_name")
    private String first_name;

    @Column(nullable = false, name="last_name")
    private String last_name;

    @Column(nullable = false, name="email")
    private String email;

    @Column(nullable = false, name="alive")
    private Boolean alive;

    public Author(String first_name, String last_name, String email, Boolean alive) {
        this.first_name = first_name;
        this.email = email;
        this.last_name = last_name;
        this.alive = alive;
    }

    public Author(Integer id) {
        this.id = id;
    }

}