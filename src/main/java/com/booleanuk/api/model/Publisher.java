package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String location;

    @OneToMany(mappedBy = "publisher")
    @JsonIgnoreProperties("publisher")
    private List<Book> books;

    public Publisher(Integer id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Publisher(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Publisher() {}

    public  Publisher(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
