package com.jonathangf.gestion.spring.jokes.mvc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jonathangf.gestion.spring.jokes.mvc.dao.ItypesDao;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Types;
import com.jonathangf.gestion.spring.jokes.mvc.services.ITypesService;

import jakarta.validation.Valid;

/**
 * Implementación de la interfaz ITypesService
 */
@Service
public class TypesServiceImpl implements ITypesService {

	@Autowired
	private ItypesDao typesDao;

	@Override
	@Transactional(readOnly = true)
	public List<Types> findAll() {
		return (List<Types>) typesDao.findAll();

	}

	@Override
	@Transactional(readOnly = true)
	public Types findById(int id) {
		return typesDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Types type) {
		typesDao.delete(type);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		typesDao.deleteById(id);
	}

	@Override
	@Transactional
	public Types save(@Valid Types type) {
		return typesDao.save(type);
	}

	@Override
	@Transactional(readOnly = true)
	public Types findByTypeIgnoreCase(String type) {
		return typesDao.findByTypeIgnoreCase(type);
	}
}
