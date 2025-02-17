package com.jonathangf.gestion.spring.jokes.mvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jonathangf.gestion.spring.jokes.mvc.entity.Flags;

/**
 * Repositorio de la entidad Flags.
 */
@Repository
public interface IflagsDao extends JpaRepository<Flags, Integer> {

	@Transactional
	@Modifying
	@Query("UPDATE Flags f SET f.jokeses = NULL WHERE f.id = :id")
	public void desvincularJokes(int id);

	public Flags findByFlagIgnoreCase(String description);
}
