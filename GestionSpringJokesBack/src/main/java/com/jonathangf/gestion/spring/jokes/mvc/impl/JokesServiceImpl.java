package com.jonathangf.gestion.spring.jokes.mvc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jonathangf.gestion.spring.jokes.mvc.dao.IjokesDao;
import com.jonathangf.gestion.spring.jokes.mvc.dto.FlagJokeDto;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Jokes;
import com.jonathangf.gestion.spring.jokes.mvc.services.IJokesService;

/**
 * Implementación de la interfaz IJokesService
 */
@Service
public class JokesServiceImpl implements IJokesService {

	@Autowired
	private IjokesDao jokesDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Jokes> findAll() {
		List<Jokes> jokes = (List<Jokes>) jokesDAO.findAll();
		System.out.println("Cantidad de chistes encontrados: " + jokes.size());
		return jokes;
	}

	@Override
	@Transactional(readOnly = true)
	public Jokes findById(int id) {
		return jokesDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Jokes save(Jokes joke) {
		return jokesDAO.save(joke);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		jokesDAO.deleteById(id);

	}

	@Override
	@Transactional
	public void delete(Jokes joke) {
		jokesDAO.delete(joke);

	}

	@Override
	@Transactional
	public void update() {

	}

	// NUEVOS MÉTODOS:

	@Override
	@Transactional(readOnly = true)
	public List<Jokes> filtrarPorTexto(String texto) {
		return jokesDAO.filtrarPorText1(texto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Jokes> findJokesSinPrimeraVez() {
		return jokesDAO.findJokesSinPrimeraVez();
	}

	@Override
	@Transactional(readOnly = true)
	public List<FlagJokeDto> findJokesByFlagId(int flagId) {
		return jokesDAO.findJokesByFlag(flagId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Jokes> findByCategoryId(int id) {
		return jokesDAO.findByCategoryId(id);
	}

	@Override
	@Transactional
	public void deleteByCategoryId(int id) {
		jokesDAO.deleteByCategoryId(id);

	}

	@Override
	@Transactional
	public void deleteByTypeId(int id) {
		jokesDAO.deleteByTypeId(id);

	}

	@Override
	@Transactional
	public void deleteByLanguageId(int id) {
		jokesDAO.deleteByLanguageId(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Jokes> findJokesWithoutPrimeraVez() {
		return jokesDAO.findJokesWithoutPrimeraVez();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Jokes> findAllWithPrimeraVezAndTelefonos() {
		return jokesDAO.findAllWithPrimeraVezAndTelefonos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Jokes> findJokesConPrimeraVez() {
		return jokesDAO.findJokesConPrimeraVez();
	}
}
