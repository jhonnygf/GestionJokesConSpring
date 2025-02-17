package com.jonathangf.gestion.spring.jokes.mvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jonathangf.gestion.spring.jokes.mvc.entity.Types;

/**
 * Repositorio de Types
 */
@Repository
public interface ItypesDao extends JpaRepository<Types, Integer> {
	public Types findByTypeIgnoreCase(String type);
}
