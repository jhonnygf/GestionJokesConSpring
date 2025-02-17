package com.jonathangf.gestion.spring.jokes.mvc.dto;

import java.io.Serializable;

import com.jonathangf.gestion.spring.jokes.mvc.entity.Jokes;

/**
 * Clase que representa un chiste con su texto
 */
public class JokesDtoDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String text1;
	private String text2;

	public JokesDtoDetail() {
	}

	public JokesDtoDetail(Jokes joke) {
		this.id = joke.getId();
		this.text1 = joke.getText1();
		this.text2 = joke.getText2();
	}

	public int getId() {
		return id;
	}

	public String getText1() {
		return text1;
	}

	public String getText2() {
		return text2;
	}

}
