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
@Table(name="publishers")
public class Publisher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String name;

	@Column
	private String location;

	@OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Book> books;
}
