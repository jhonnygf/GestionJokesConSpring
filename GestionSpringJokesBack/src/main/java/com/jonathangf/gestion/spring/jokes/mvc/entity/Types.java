package com.jonathangf.gestion.spring.jokes.mvc.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase que representa la tabla types de la base de datos.
 */
@Entity
@Table(name = "types")
public class Types implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@Column(name = "type", nullable = false)
	private String type;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "types", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("types")
	private Set<Jokes> jokeses = new HashSet<Jokes>(0);

	public Types() {
	}

	public Types(String type) {
		this.type = type;
	}

	public Types(int id, String type, Set<Jokes> jokeses) {
		this.id = id;
		this.type = type;
		this.jokeses = jokeses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<Jokes> getJokeses() {
		return this.jokeses;
	}

	public void setJokeses(Set<Jokes> jokeses) {
		this.jokeses = jokeses;
	}
}
