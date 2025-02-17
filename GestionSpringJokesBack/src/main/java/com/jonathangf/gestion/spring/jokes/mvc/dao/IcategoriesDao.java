package com.jonathangf.gestion.spring.jokes.mvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repositorio de la entidad Categories
 */
import org.springframework.stereotype.Repository;

import com.jonathangf.gestion.spring.jokes.mvc.entity.Categories;

@Repository
public interface IcategoriesDao extends JpaRepository<Categories, Integer> {

	public Categories findByCategoryIgnoreCase(String category);
}
