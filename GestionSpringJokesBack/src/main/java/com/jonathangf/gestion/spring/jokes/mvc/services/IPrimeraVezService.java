package com.jonathangf.gestion.spring.jokes.mvc.services;

import java.util.List;

import com.jonathangf.gestion.spring.jokes.mvc.entity.PrimeraVez;

import jakarta.validation.Valid;

/**
 * Interfaz de la clase PrimeraVezService
 */
public interface IPrimeraVezService {

	public List<PrimeraVez> findAll();

	public PrimeraVez findById(int id);

	public PrimeraVez findByJokeId(int jokeId);

	public void deleteById(int id);

	public void delete(PrimeraVez primeraVez);

	public PrimeraVez save(@Valid PrimeraVez primeraVez);

	public boolean existsByJokeId(int jokeId);

}
