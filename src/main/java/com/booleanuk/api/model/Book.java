package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String title;

	@Column
	private String genre;

	// Many-to-One relationship with Author (automatically manages author_id foreign key)
	@ManyToOne
	@JoinColumn(name = "author_id")  // This is where the foreign key "author_id" will be mapped
	@JsonIgnoreProperties(value = {"books"})
	@JsonIgnore
	private Author author;

	// Many-to-One relationship with Publisher (automatically manages publisher_id foreign key)
	@ManyToOne
	@JoinColumn(name = "publisher_id")  // This is where the foreign key "publisher_id" will be mapped
	@JsonIgnoreProperties(value = {"books"})
	@JsonIgnore
	private Publisher publisher;
}
