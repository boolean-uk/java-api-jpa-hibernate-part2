package com.booleanuk.api.model;

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
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "genre")
	private String genre;

	@ManyToOne
	@JsonIgnoreProperties(value = "books")
	@JoinColumn(name = "author_id", referencedColumnName = "id")
	private Author author;

	@ManyToOne
	@JsonIgnoreProperties(value = "books")
	@JoinColumn(name = "publisher_id", referencedColumnName = "id")
	private Publisher publisher;

	public Book(String title, String genre, Author author, Publisher publisher) {
		this.title = title;
		this.genre = genre;
		this.author = author;
		this.publisher = publisher;
	}

	public Book(int id) {
		this.id = id;
	}
}
