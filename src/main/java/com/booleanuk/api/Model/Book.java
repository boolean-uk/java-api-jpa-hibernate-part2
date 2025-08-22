package com.booleanuk.api.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;
    @Getter
    @Setter
    @Column(name = "title")
    private String title;
    @Getter
    @Setter
    @Column(name = "genre")
    private String genre;
    @Getter
    @Setter
    @Column(name = "author_id")
    private int author_id;
    @Getter
    @Setter
    @Column(name = "publisher_id")
    private int publisher_id;








}
