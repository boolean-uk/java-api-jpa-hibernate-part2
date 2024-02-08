package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "publisher")
    @JsonIncludeProperties("publisher")
    private ArrayList<Book> books;

    public Publisher(){
        super();
    }

    public Publisher(int id) {
        Id = id;
    }

    public Publisher(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
