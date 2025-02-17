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
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase Language que representa la tabla language de la base de datos.
 */
@NamedQuery(name = "Language.findByTexto", query = "FROM Language l WHERE LOWER(l.language) LIKE :language")
@NamedQuery(name = "Language.noJokes", query = "SELECT l FROM Language l WHERE l.jokeses IS EMPTY")
@Entity
@Table(name = "language")
public class Language implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	@Column(name = "code", length = 2)
	private String code;
	@Column(name = "language")
	private String language;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "language")
	@JsonIgnoreProperties("language")
	private Set<Jokes> jokeses = new HashSet<Jokes>(0);

	public Language() {
	}


	public Language(String language) {
		this.language = language;
	}

	public Language(int id, String code, String language, Set<Jokes> jokeses) {
		this.id = id;
		this.code = code;
		this.language = language;
		this.jokeses = jokeses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Set<Jokes> getJokeses() {
		return this.jokeses;
	}

	public void setJokeses(Set<Jokes> jokeses) {
		this.jokeses = jokeses;
	}
}
