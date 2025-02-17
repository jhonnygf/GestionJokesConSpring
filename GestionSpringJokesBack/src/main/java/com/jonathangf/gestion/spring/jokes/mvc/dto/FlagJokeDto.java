package com.jonathangf.gestion.spring.jokes.mvc.dto;

/**
 * Clase que representa un chiste con la bandera de su idioma
 */
public class FlagJokeDto {
	private int idJoke;
	private String text1;
	private String language;

	public FlagJokeDto(int idJoke, String text1, String language) {
		this.idJoke = idJoke;
		this.text1 = text1;
		this.language = language;
	}

	public int getIdJoke() {
		return idJoke;
	}

	public void setIdJoke(int idJoke) {
		this.idJoke = idJoke;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
