package com.nael.catalog.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "book")
public class Book extends AbstractBaseEntity {

	// ingat!! dependency itu ketergantungan artinya, ketergantungan suatu kelas
	// terhadap kelas lain
	// jika kita menggunakan hardcode dependency
	// private Author author = new Author();

	/**
	 * 
	 */
	private static final long serialVersionUID = 9205011064570708314L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	// TODO: lanjut untuk buat entity book
	@Column(name = "description", nullable = true, columnDefinition = "TEXT")
	private String description;

	// contoh relasi many to one: default FetchType nya adalh Eiger, kita ubah ke
	// lazy
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "publisher_id", nullable = false)
	private Publisher publisher;

	@ManyToMany
	@JoinTable(name = "book_author", joinColumns = {
			@JoinColumn(name = "book_id", referencedColumnName = "id")
	}, inverseJoinColumns = {
			@JoinColumn(name = "author_id", referencedColumnName = "id")
	})
	private List<Author> authors;

	@ManyToMany
	@JoinTable(name = "book_category", joinColumns = {
			@JoinColumn(name = "book_id", referencedColumnName = "id")
	}, inverseJoinColumns = {
			@JoinColumn(name = "category_code", referencedColumnName = "code")
	})
	private List<Category> categories;

}
