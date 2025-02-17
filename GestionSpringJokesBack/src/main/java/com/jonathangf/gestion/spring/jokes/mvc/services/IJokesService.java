package com.jonathangf.gestion.spring.jokes.mvc.services;

import java.util.List;

import com.jonathangf.gestion.spring.jokes.mvc.dto.FlagJokeDto;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Jokes;

import jakarta.validation.Valid;

/**
 * Interfaz que define los métodos que puede realizar el servicio de Jokes.
 */
public interface IJokesService {

	public List<Jokes> findAll();

	public Jokes findById(int id);

	public void deleteById(int id);

	public void delete(Jokes joke);

	public Jokes save(@Valid Jokes joke);

	public void update();

	public List<Jokes> filtrarPorTexto(String texto);

	public List<Jokes> findJokesSinPrimeraVez();

	public List<FlagJokeDto> findJokesByFlagId(int flagId);

	public List<Jokes> findByCategoryId(int id);

	public void deleteByCategoryId(int id);

	public void deleteByTypeId(int id);

	public void deleteByLanguageId(int id);

	public List<Jokes> findJokesWithoutPrimeraVez();

	public List<Jokes> findJokesConPrimeraVez();

	public List<Jokes> findAllWithPrimeraVezAndTelefonos();
}
