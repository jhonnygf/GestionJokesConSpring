package com.jonathangf.gestion.spring.jokes.mvc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jonathangf.gestion.spring.jokes.mvc.dao.IlanguageDao;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Language;
import com.jonathangf.gestion.spring.jokes.mvc.services.ILanguageService;

import jakarta.validation.Valid;

@Service
public class LanguageServiceImpl implements ILanguageService {
	@Autowired
	private IlanguageDao languageDao;

	@Override
	@Transactional(readOnly=true)
	public List<Language> findAll() {
		return (List<Language>) languageDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Language findById(int id) {
		return languageDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Language language) {
		languageDao.delete(language);
		
	}

	@Override
	@Transactional
	public Language save(@Valid Language language) {
		return languageDao.save(language);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		languageDao.deleteById(id);
		
	}

	@Override
	public Language findByLanguageIgnoreCase(String language) {
		return languageDao.findByLanguageIgnoreCase(language);
	}
}
