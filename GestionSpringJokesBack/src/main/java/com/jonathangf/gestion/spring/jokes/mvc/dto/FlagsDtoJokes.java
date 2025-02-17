package com.jonathangf.gestion.spring.jokes.mvc.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase que representa una bandera con los chistes que tiene asociados
 */
public class FlagsDtoJokes implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String flag;
	private Set<String> jokes = new HashSet<>();

	public FlagsDtoJokes() {
	}

	public FlagsDtoJokes(int id, String flag, Set<String> jokes) {
		this.id = id;
		this.flag = flag;
		this.jokes = jokes;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Set<String> getJokes() {
		return jokes;
	}

	public void setJokes(Set<String> jokes) {
		this.jokes = jokes;
	}
}
