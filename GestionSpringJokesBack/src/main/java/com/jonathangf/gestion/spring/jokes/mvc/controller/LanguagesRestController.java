package com.jonathangf.gestion.spring.jokes.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jonathangf.gestion.spring.jokes.mvc.dto.LanguagesDto;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Language;
import com.jonathangf.gestion.spring.jokes.mvc.services.IJokesService;
import com.jonathangf.gestion.spring.jokes.mvc.services.ILanguageService;

import jakarta.validation.Valid;

/**
 * Controlador REST para el recurso Lenguajes
 */
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE, RequestMethod.OPTIONS })
@RestController
@RequestMapping("/api/languages")
public class LanguagesRestController {

	@Autowired
	private ILanguageService languagesService;

	@Autowired
	private IJokesService jokesService;

	/**
	 * Listar todos los lenguajes
	 * @return
	 */
	@GetMapping({ "/", "" })
	public List<LanguagesDto> listarLenguajes() {
		List<Language> lenguajes = languagesService.findAll();
		return lenguajes.stream().map(LanguagesDto::new).collect(Collectors.toList());
	}

	/**
	 * Buscar un lenguaje por ID
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarLenguaje(@PathVariable int id) {
		Map<String, Object> response = new HashMap<>();

		try {
			Language lenguaje = languagesService.findById(id);

			if (lenguaje == null) {
				response.put("mensaje", "El lenguaje con ID " + id + " no existe en la base de datos.");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			LanguagesDto languageDto = new LanguagesDto(lenguaje);

			return new ResponseEntity<>(languageDto, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos.");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Crear una nueva categoría
	 */
	@PostMapping("/nuevo")
	public ResponseEntity<?> crearLenguaje(@Valid @RequestBody Language lenguaje, BindingResult result) {
		Map<String, Object> response = new HashMap<>();

		if (lenguaje.getId() != 0) {
			response.put("mensaje", "No debes proporcionar un ID. El sistema lo generará automáticamente.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errores);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		Language existingLanguage = languagesService.findByLanguageIgnoreCase(lenguaje.getLanguage());
		if (existingLanguage != null) {
			response.put("mensaje", "El lenguaje con el nombre '" + lenguaje.getLanguage() + "' ya existe.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			Language nuevoLenguaje = languagesService.save(lenguaje);
			response.put("mensaje", "El lenguaje ha sido creado con éxito.");
			response.put("lenguaje", nuevoLenguaje);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al guardar el lenguaje en la base de datos.");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Editar una categoría existente
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<?> actualizarLenguajes(@Valid @RequestBody Language lenguaje, BindingResult result,
			@PathVariable int id) {
		Language lenguajeActual = languagesService.findById(id);
		Map<String, Object> response = new HashMap<>();

		if (lenguajeActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el lenguaje con ID " + id + " no existe.");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		try {
			lenguajeActual.setLanguage(lenguaje.getLanguage()); // Solo permite editar el nombre
			Language languageActualizado = languagesService.save(lenguajeActual);

			response.put("mensaje", "El lenguaje ha sido actualizada con éxito.");
			response.put("lenguaje", languageActualizado);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el lenguaje en la base de datos.");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Eliminar una categoría por ID
	 */
	@DeleteMapping("/borrar/{id}")
	public ResponseEntity<?> eliminarLenguaje(@PathVariable int id) {
		Language lenguaje = languagesService.findById(id);
		Map<String, Object> response = new HashMap<>();

		if (lenguaje == null) {
			response.put("mensaje", "El lenguaje con ID " + id + " no existe en la base de datos.");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		try {
			//Borrar los chistes asociados a este lenguaje
			jokesService.deleteByLanguageId(id);

			//Borrar el lenguaje
			languagesService.deleteById(id);

			response.put("mensaje", "Lenguaje y chistes asociados eliminados con éxito.");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el lenguaje de la base de datos.");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
