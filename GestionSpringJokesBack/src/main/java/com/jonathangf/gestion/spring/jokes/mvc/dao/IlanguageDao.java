package com.jonathangf.gestion.spring.jokes.mvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Language;

/**
 * Repositorio de Language
 */
@Repository
public interface IlanguageDao extends JpaRepository<Language, Integer> {

	public Language findByLanguageIgnoreCase(String language);
}
