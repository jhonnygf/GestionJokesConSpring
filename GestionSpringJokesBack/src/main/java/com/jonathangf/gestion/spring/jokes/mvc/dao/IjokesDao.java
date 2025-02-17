package com.jonathangf.gestion.spring.jokes.mvc.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.jonathangf.gestion.spring.jokes.mvc.dto.FlagJokeDto;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Jokes;

/**
 * Repositorio de Jokes
 */
@Repository
public interface IjokesDao extends JpaRepository<Jokes, Integer>{
	// Filtro por text1 (no sensible a mayúsculas)
    @Query("SELECT j FROM Jokes j "
         + "WHERE LOWER(j.text1) LIKE LOWER(CONCAT('%', :texto, '%'))")
    public List<Jokes> filtrarPorText1(@Param("texto") String texto);

    // Listar jokes SIN primeraVez asociada (id NOT IN subconsulta)
    @Query("SELECT j FROM Jokes j "
         + "WHERE j.id NOT IN (SELECT pv.joke.id FROM PrimeraVez pv)")
    public List<Jokes> findJokesSinPrimeraVez();

    // Para el Flag -> Jokes -> Language (usamos el DTO FlagJokeDto)
    @Query("SELECT new com.jonathangf.gestion.spring.jokes.mvc.dto.FlagJokeDto("
         + " j.id, j.text1, j.language.language) "
         + "FROM Jokes j JOIN j.flagses f "
         + "WHERE f.id = :flagId")
    public List<FlagJokeDto> findJokesByFlag(@Param("flagId") int flagId);

    @Query("SELECT j FROM Jokes j WHERE j.categories.id = :categoryId")
    public List<Jokes> findByCategoryId(@Param("categoryId") int categoryId);

    @Modifying
    @Query("DELETE FROM Jokes j WHERE j.categories.id = :categoryId")
    public void deleteByCategoryId(@Param("categoryId") int categoryId);

    @Modifying
    @Query("DELETE FROM Jokes j WHERE j.types.id = :typeId")
    public void deleteByTypeId(@Param("typeId") int typeId);
    
    @Modifying
    @Query("DELETE FROM Jokes j WHERE j.language.id = :languageId")
    public void deleteByLanguageId(@Param("languageId") int languageId);
    
    @Query("SELECT j FROM Jokes j WHERE j.primeraVez IS NULL")
    public List<Jokes> findJokesWithoutPrimeraVez();
    
    @Query("SELECT j FROM Jokes j LEFT JOIN FETCH j.primeraVez pv LEFT JOIN FETCH pv.telefonos")
    public List<Jokes> findAllWithPrimeraVezAndTelefonos();
    
    @Query("SELECT j FROM Jokes j WHERE j.primeraVez IS NOT NULL")
    public List<Jokes> findJokesConPrimeraVez();
}
