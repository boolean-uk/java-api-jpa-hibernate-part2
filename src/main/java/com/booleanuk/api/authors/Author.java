package com.booleanuk.api.authors;

import java.util.ArrayList;
import java.util.List;

import com.booleanuk.api.books.Book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
  // Spec technically specifies snake_case, but that is not the convention for
  // json so I will do it in camelcase anyways
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  @Email
  private String email;

  @Column(nullable = false)
  private Boolean alive;

  @OneToMany(mappedBy = "authorId", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties("authorId")
  private List<Book> books = new ArrayList<>();

  public Author(int id) {
    this.id = id;
  }
}
