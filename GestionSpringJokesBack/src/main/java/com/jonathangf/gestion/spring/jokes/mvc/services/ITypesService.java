package com.jonathangf.gestion.spring.jokes.mvc.services;

import java.util.List;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Types;

import jakarta.validation.Valid;

/**
 * Interfaz de Types
 */
public interface ITypesService {

	public List<Types> findAll();

	public Types findById(int id);

	public void delete(Types type);

	public void deleteById(int id);

	public Types save(@Valid Types type);

	public Types findByTypeIgnoreCase(String type);
}
