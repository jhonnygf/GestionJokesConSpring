package com.jonathangf.gestion.spring.jokes.mvc.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import com.jonathangf.gestion.spring.jokes.mvc.entity.Jokes;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Types;

/**
 * Clase que representa un objeto de transferencia de datos (DTO) de la entidad
 * Types
 */
public class TypesDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String type;
	private Set<String> jokes;

	public TypesDto() {
	}

	public TypesDto(Types type) {
		this.id = type.getId();
		this.type = type.getType();
		this.jokes = type.getJokeses().stream().map(Jokes::getText1).collect(Collectors.toSet());
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public Set<String> getJokes() {
		return jokes;
	}

	public void setJokes(Set<String> jokes) {
		this.jokes = jokes;
	}

}
