package com.jonathangf.gestion.spring.jokes.mvc.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import com.jonathangf.gestion.spring.jokes.mvc.entity.Categories;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Jokes;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) de la entidad
 */
public class CategoriesDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String category;
	private Set<String> jokes;
	private int jokesCount;

	public CategoriesDto(Categories category) {
		this.id = category.getId();
		this.category = category.getCategory();
		this.jokes = category.getJokeses().stream().map(Jokes::getText1).collect(Collectors.toSet());
		this.jokesCount = category.getJokeses() != null ? category.getJokeses().size() : 0;
	}

	public int getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public Set<String> getJokes() {
		return jokes;
	}

	public int getJokesCount() {
		return jokesCount;
	}

}
