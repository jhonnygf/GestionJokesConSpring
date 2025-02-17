package com.jonathangf.gestion.spring.jokes.mvc.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Jokes;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Language;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) de la entidad
 * Language
 */
public class LanguagesDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String language;
	private Set<String> jokes;

	public LanguagesDto() {
	}

	public LanguagesDto(Language language) {
		this.id = language.getId();
		this.language = language.getLanguage();
		this.jokes = language.getJokeses().stream().map(Jokes::getText1).collect(Collectors.toSet());

	}

	public int getId() {
		return id;
	}

	public String getLanguage() {
		return language;
	}

	public Set<String> getJokes() {
		return jokes;
	}

}
