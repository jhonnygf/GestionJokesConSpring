package com.jonathangf.gestion.spring.jokes.mvc.dto;

import java.io.Serializable;

/**
 * Clase que representa una bandera con la cantidad de chistes que tiene
 * asociada
 */
public class FlagsDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String flag;
	private int jokeCount;

	public FlagsDto() {
	}

	public FlagsDto(int id, String flag, int jokeCount) {
		this.id = id;
		this.flag = flag;
		this.jokeCount = jokeCount;
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

	public int getJokeCount() {
		return jokeCount;
	}

	public void setJokeCount(int jokeCount) {
		this.jokeCount = jokeCount;
	}

}
