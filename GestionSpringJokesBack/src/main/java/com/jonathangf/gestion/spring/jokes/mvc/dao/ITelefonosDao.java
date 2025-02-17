package com.jonathangf.gestion.spring.jokes.mvc.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Telefonos;

/**
 * Repositorio de la entidad Telefonos.
 */
@Repository
public interface ITelefonosDao extends CrudRepository<Telefonos, Integer> {

}
