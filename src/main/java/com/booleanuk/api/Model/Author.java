package com.booleanuk.api.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean alive;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    public Author(String firstName, String lastName, String email, boolean alive){
        this.firstName=firstName;
        this.lastName =lastName;
        this.alive=alive;
        this.email=email;
    }

    public Author(int id){
        this.id=id;
    }

    public Author(){

    }





    public Integer getId() {return id;}

    public String getEmail() { return email;}

    public String getFirstName() {return firstName;}

    public String getLastName() {return lastName;}

    public boolean isAlive() {return alive;}

    public void setId(Integer id) {this.id = id;}

    public void setEmail(String email) {this.email = email;}

    public void setAlive(boolean alive) {this.alive = alive;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public void setLastName(String lastName) {this.lastName = lastName;}
}
