package com.jonathangf.gestion.spring.jokes.mvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jonathangf.gestion.spring.jokes.mvc.entity.PrimeraVez;

/**
 * Repositorio de PrimeraVez
 */
@Repository
public interface IPrimeraVezDao extends JpaRepository<PrimeraVez, Integer> {
	@Query("SELECT pv FROM PrimeraVez pv WHERE pv.joke.id = :idJoke")
	PrimeraVez findByJokeId(@Param("idJoke") int idJoke);

	@Query("SELECT COUNT(p) > 0 FROM PrimeraVez p WHERE p.joke.id = :jokeId")
	boolean existsByJokeId(@Param("jokeId") int jokeId);

}
