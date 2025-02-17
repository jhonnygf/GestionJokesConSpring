package com.jonathangf.gestion.spring.jokes.mvc.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) para la
 * entidad PrimeraVez.
 */
public class PrimeraVezDto {
	private int id;
	private String programa;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // ✅ Formato JSON
	private LocalDate fechaEmision;
	private int jokeId;
	private List<String> telefonos;

	public PrimeraVezDto(int id, String programa, LocalDate fechaEmision, int jokeId, List<String> telefonos) {
		this.id = id;
		this.programa = programa;
		this.fechaEmision = fechaEmision;
		this.jokeId = jokeId;
		this.telefonos = telefonos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public void setFechaEmision(LocalDate fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public int getJokeId() {
		return jokeId;
	}

	public void setJokeId(int jokeId) {
		this.jokeId = jokeId;
	}

	public List<String> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(List<String> telefonos) {
		this.telefonos = telefonos;
	}

}
