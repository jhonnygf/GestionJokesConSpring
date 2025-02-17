package com.jonathangf.gestion.spring.jokes.mvc.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase Categories que representa la tabla categories de la base de datos.
 */
@Entity
@Table(name = "categories")
public class Categories implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@Column(name = "category", nullable = false)
	private String category;
	@JsonIgnoreProperties("categories")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "categories")	
	private Set<Jokes> jokeses = new HashSet<Jokes>(0);

	public Categories() {
	}

	public Categories(String category) {
		this.category = category;
	}

	public Categories(int id, String category, Set<Jokes> jokeses) {
		this.id = id;
		this.category = category;
		this.jokeses = jokeses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Set<Jokes> getJokeses() {
		return this.jokeses;
	}

	public void setJokeses(Set<Jokes> jokeses) {
		this.jokeses = jokeses;
	}

	@Override
	public String toString() {
		return "Categories [id=" + id + ", category=" + category + "]";
	}
}
