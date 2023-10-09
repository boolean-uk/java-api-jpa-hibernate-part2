package com.booleanuk.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long publisherId;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "location")
    @NotBlank
    private String location;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
    private List<Book> books;


}
