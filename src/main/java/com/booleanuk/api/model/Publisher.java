package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "publishers")
public class Publisher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "location")
	private String location;

	@OneToMany(mappedBy = "publisher")
	@JsonIgnoreProperties(value = {"author","publisher"})
	private List<Book> books;

	public Publisher(int id) {
		this.id = id;
	}

	public Publisher(String name, String location) {
		this.name = name;
		this.location = location;
	}
}
