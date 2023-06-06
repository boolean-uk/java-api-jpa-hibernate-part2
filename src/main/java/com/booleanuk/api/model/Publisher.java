package com.booleanuk.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "publishers")
public class Publisher {

    //table columns
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;


    //constructors
    public Publisher() {
        super();
    }

    public Publisher(String name, String location) {
        super();
        this.name = name;
        this.location = location;
    }

    //getters and setters

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
