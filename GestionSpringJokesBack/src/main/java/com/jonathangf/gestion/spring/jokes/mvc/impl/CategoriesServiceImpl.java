package com.jonathangf.gestion.spring.jokes.mvc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jonathangf.gestion.spring.jokes.mvc.dao.IcategoriesDao;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Categories;
import com.jonathangf.gestion.spring.jokes.mvc.services.ICategoriesService;

/**
 * Implementación de la interfaz ICategoriesService
 */
@Service
public class CategoriesServiceImpl implements ICategoriesService {

	@Autowired
	private IcategoriesDao categoriesDao;

	@Override
	@Transactional(readOnly = true)
	public List<Categories> findAll() {
		return (List<Categories>) categoriesDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Categories findById(int id) {
		return categoriesDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Categories category) {
		categoriesDao.delete(category);

	}

	@Override
	@Transactional
	public Categories save(Categories category) {
		return categoriesDao.save(category);

	}

	@Override
	public void deleteById(int id) {
		categoriesDao.deleteById(id);

	}

	@Override
	public Categories findByCategoryIgnoreCase(String category) {
		return categoriesDao.findByCategoryIgnoreCase(category);
	}
}
