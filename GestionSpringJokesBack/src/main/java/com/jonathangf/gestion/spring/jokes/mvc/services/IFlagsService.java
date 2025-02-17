package com.jonathangf.gestion.spring.jokes.mvc.services;

import java.util.List;

import com.jonathangf.gestion.spring.jokes.mvc.entity.Flags;

import jakarta.validation.Valid;

/**
 * Interfaz de la clase FlagsService
 */
public interface IFlagsService {

	public List<Flags> findAll();

	public Flags findById(int id);

	public void delete(Flags flag);

	public Flags save(@Valid Flags flag);

	public void deleteById(int id);

	public void desvincularJokes(int id);

	public Flags findByFlagIgnoreCase(String flag);
}
