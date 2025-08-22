package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name="Authors")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;


	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String email;

	@Column
	private boolean alive;

	@OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Book> books;
}
