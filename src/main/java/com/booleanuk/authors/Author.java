package com.booleanuk.authors;

import java.util.List;
import com.booleanuk.books.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @Column
    private String first_name;
    @Column
    private String last_name;
    @Column
    private String email;
    @Column
    private boolean alive;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties({ "author", "id", "publisher" })
    private List<Book> books;

    public Author(String first_name, String last_name, String email, boolean alive) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.alive = alive;
    }

    public Author(int id) {
        this.id = id;
    }
}
