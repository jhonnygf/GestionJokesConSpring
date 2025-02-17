package com.jonathangf.gestion.spring.jokes.mvc.services;

import java.util.List;

import com.jonathangf.gestion.spring.jokes.mvc.entity.Language;

import jakarta.validation.Valid;

public interface ILanguageService {

	public List<Language> findAll();
	public Language findById(int id);
	public void delete(Language language);
	public void deleteById(int id);
	public Language save(@Valid Language language);
	public Language findByLanguageIgnoreCase(String language);
}
