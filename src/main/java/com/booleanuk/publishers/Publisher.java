package com.booleanuk.publishers;

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
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;
    @Column
    private String location;

    @OneToMany(mappedBy = "publisher")
    @JsonIgnoreProperties({ "author", "id", "publisher" })
    private List<Book> books;

    public Publisher(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Publisher(int id) {
        this.id = id;
    }
}
