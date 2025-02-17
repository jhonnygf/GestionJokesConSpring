package com.jonathangf.gestion.spring.jokes.mvc.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) para la entidad Jokes.
 */
public class JokesDtoConPrimera {

	private int idJoke;
	private String text1;
	private String text2;
	private String category;
	private String language;
	private Set<String> flagses;
	private boolean hasPrimeraVez;
	private String programa;
	private LocalDate fechaEmision;
	private List<String> telefonos;

	public JokesDtoConPrimera() {
	}

	public JokesDtoConPrimera(int idJoke, String text1, String text2, String category, String language,
			Set<String> flags, boolean hasPrimeraVez, String programa, LocalDate fechaEmision, List<String> telefonos) {
		this.idJoke = idJoke;
		this.text1 = text1;
		this.text2 = text2;
		this.category = category;
		this.language = language;
		this.flagses = flags;
		this.hasPrimeraVez = hasPrimeraVez;
		this.programa = programa;
		this.fechaEmision = fechaEmision;
		this.telefonos = telefonos;
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

	public boolean isHasPrimeraVez() {
		return hasPrimeraVez;
	}

	public void setHasPrimeraVez(boolean hasPrimeraVez) {
		this.hasPrimeraVez = hasPrimeraVez;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public LocalDate getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(LocalDate localDate) {
		this.fechaEmision = localDate;
	}

	public List<String> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<String> telefonos) {
		this.telefonos = telefonos;
	}
}
