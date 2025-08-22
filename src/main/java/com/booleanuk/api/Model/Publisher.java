package com.booleanuk.api.Model;


import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;
    @Getter
    @Setter
    @Column(name = "name")
    private String name;
    @Getter
    @Setter
    @Column(name = "location")
    private String location;









}
