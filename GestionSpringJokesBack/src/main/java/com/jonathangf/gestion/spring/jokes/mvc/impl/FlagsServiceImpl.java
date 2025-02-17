package com.jonathangf.gestion.spring.jokes.mvc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jonathangf.gestion.spring.jokes.mvc.dao.IflagsDao;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Flags;
import com.jonathangf.gestion.spring.jokes.mvc.services.IFlagsService;

import jakarta.validation.Valid;

/**
 * Implementación de la interfaz IFlagsService
 */
@Service
public class FlagsServiceImpl implements IFlagsService {

	@Autowired
	private IflagsDao flagsDao;

	@Override
	@Transactional(readOnly = true)
	public List<Flags> findAll() {
		return (List<Flags>) flagsDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Flags findById(int id) {
		return flagsDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Flags flag) {
		flagsDao.delete(flag);

	}

	@Override
	@Transactional
	public Flags save(@Valid Flags flag) {
		return flagsDao.save(flag);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		flagsDao.deleteById(id);

	}

	@Override
	@Transactional
	public void desvincularJokes(int id) {
		flagsDao.desvincularJokes(id);
	}

	@Override
	public Flags findByFlagIgnoreCase(String flag) {
		return flagsDao.findByFlagIgnoreCase(flag);
	}

}
