package com.jonathangf.gestion.spring.jokes.mvc.services;

import java.util.List;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Categories;
import jakarta.validation.Valid;

/**
 * Interfaz de la capa de servicios para la entidad Categories.
 */
public interface ICategoriesService {

	public List<Categories> findAll();

	public Categories findById(int id);

	public void delete(Categories category);

	public Categories save(@Valid Categories category);

	public void deleteById(int id);

	public Categories findByCategoryIgnoreCase(String category);
}
