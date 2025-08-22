package com.booleanuk.api.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books;

    public Publisher(String name, String location){
        this.name=name;
        this.location=location;
    }

    public Publisher(int id){
        this.id=id;
    }

    public Publisher(){

    }

    public Integer getId() {return id;}

    public String getName() { return name;}

    public String getLocation() {return location;}

    public void setId(Integer id) {this.id = id;}

    public void setName(String name) {this.name = name;}

    public void setLocation(String location) {this.location = location;}
}
