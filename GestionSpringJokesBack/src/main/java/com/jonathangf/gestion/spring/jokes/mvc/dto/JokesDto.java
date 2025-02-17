package com.jonathangf.gestion.spring.jokes.mvc.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) de la entidad
 * Jokes
 */
public class JokesDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String text1;
	private String text2;
	private String category;
	private String language;
	private Set<String> flagses = new HashSet<>();

	public JokesDto() {
	}

	public JokesDto(int id, String text1, String text2, String category, String language, Set<String> flagses) {
		this.id = id;
		this.text1 = text1;
		this.text2 = text2;
		this.category = category;
		this.language = language;
		this.flagses = flagses;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Set<String> getFlagses() {
		return flagses;
	}

	public void setFlagses(Set<String> flagses) {
		this.flagses = flagses;
	}
}
