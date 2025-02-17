package com.jonathangf.gestion.spring.jokes.mvc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jonathangf.gestion.spring.jokes.mvc.dao.IPrimeraVezDao;
import com.jonathangf.gestion.spring.jokes.mvc.entity.PrimeraVez;
import com.jonathangf.gestion.spring.jokes.mvc.services.IPrimeraVezService;

import jakarta.validation.Valid;

/**
 * Implementación de la interfaz IPrimeraVezService
 */
@Service
public class PrimeraVezServiceImpl implements IPrimeraVezService {

	@Autowired
	private IPrimeraVezDao primeraVezDao;

	@Override
	@Transactional(readOnly = true)
	public List<PrimeraVez> findAll() {
		return (List<PrimeraVez>) primeraVezDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public PrimeraVez findById(int id) {
		return primeraVezDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public PrimeraVez findByJokeId(int jokeId) {
		return primeraVezDao.findByJokeId(jokeId);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		primeraVezDao.deleteById(id);

	}

	@Override
	@Transactional
	public void delete(PrimeraVez primeraVez) {
		primeraVezDao.delete(primeraVez);

	}

	@Override
	@Transactional
	public PrimeraVez save(@Valid PrimeraVez primeraVez) {
		return primeraVezDao.save(primeraVez);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByJokeId(int jokeId) {
		return primeraVezDao.existsByJokeId(jokeId);
	}

}
