package com.jonathangf.gestion.spring.jokes.mvc.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jonathangf.gestion.spring.jokes.mvc.dto.FlagJokeDto;
import com.jonathangf.gestion.spring.jokes.mvc.dto.FlagsDto;
import com.jonathangf.gestion.spring.jokes.mvc.dto.FlagsDtoJokes;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Flags;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Jokes;
import com.jonathangf.gestion.spring.jokes.mvc.services.IFlagsService;
import com.jonathangf.gestion.spring.jokes.mvc.services.IJokesService;

import jakarta.validation.Valid;

/**
 * Clase que implementa el controlador REST de la entidad Flags.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE, RequestMethod.OPTIONS })
@RestController
@RequestMapping("/api/flags")
public class FlagsRestController {

	@Autowired
	private IFlagsService flagsService;

	@Autowired
	private IJokesService jokesService;

	/*
	 * Obtiene la lista de flags con sus chistes asociados.
	 */
	@GetMapping
	public ResponseEntity<?> listarDto() {
		List<Flags> flags = flagsService.findAll();

		if (flags.isEmpty()) {
			return new ResponseEntity<>("No se encontraron flags.", HttpStatus.NOT_FOUND);
		}

		List<FlagsDtoJokes> flagsDtoList = flags.stream().map(flag -> {
			Set<String> jokesText = (flag.getJokeses() != null)
					? flag.getJokeses().stream().map(Jokes::getText1).collect(Collectors.toSet())
					: new HashSet<>();

			return new FlagsDtoJokes(flag.getId(), flag.getFlag(), jokesText);
		}).collect(Collectors.toList());

		return new ResponseEntity<>(flagsDtoList, HttpStatus.OK);
	}

	/**
	 * Obtiene un flag específico con sus chistes asociados.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getFlag(@PathVariable int id) {
		Flags flag = null;
		Map<String, Object> response = new HashMap<>();
		FlagsDtoJokes flagDto = new FlagsDtoJokes();

		try {
			flag = flagsService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (flag == null) {
			response.put("mensaje",
					"El flag ID: ".concat(Integer.toString(id)).concat(" no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		flagDto.setId(flag.getId());
		flagDto.setFlag(flag.getFlag());
		Set<String> jokesText = flag.getJokeses().stream().map(Jokes::getText1).collect(Collectors.toSet());
		flagDto.setJokes(jokesText);
		return new ResponseEntity<FlagsDtoJokes>(flagDto, HttpStatus.OK);
	}

	// Obtener lista de IDs ocupados
	@GetMapping("/used-ids")
	public ResponseEntity<Set<Integer>> getUsedIds() {
		List<Flags> flags = flagsService.findAll();
		Set<Integer> usedIds = flags.stream().map(Flags::getId).collect(Collectors.toSet());
		return ResponseEntity.ok(usedIds);
	}

	/**
	 * Elimina un flag por su ID.
	 * @param id
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable int id) {
		flagsService.deleteById(id);
	}

	@DeleteMapping({ "/borrar/{id}", "/borrar/{id}/" })
	public ResponseEntity<?> delete(@PathVariable int id) {
		Flags flagBorrado = flagsService.findById(id);
		Map<String, Object> response = new HashMap<>();

		if (flagBorrado == null) {

			response.put("mensaje",
					"El flag ID: ".concat(Integer.toString(id)).concat(" no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			flagsService.deleteById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el flag de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El flag ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	/**
	 * Actualiza un flag 
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Flags flag, BindingResult result, @PathVariable int id) {
		Flags flagActual = flagsService.findById(id);
		Map<String, Object> response = new HashMap<>();

		if (flagActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el flag ID: " + id + " no existe en la base de datos!");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		try {
			flagActual.setFlag(flag.getFlag());
			Flags flagUpdated = flagsService.save(flagActual);
			FlagsDto flagDto = new FlagsDto(flagUpdated.getId(), flagUpdated.getFlag(),
					flagUpdated.getJokeses().size());

			response.put("mensaje", "El flag ha sido actualizado con éxito!");
			response.put("flag", flagDto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el flag en la base de datos");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Desvincula todos los chistes de un flag.
	 */
	@PutMapping("/desvincular/{id}")
	public ResponseEntity<?> desvincularJokes(@PathVariable int id) {
		Flags flag = flagsService.findById(id);
		if (flag == null) {
			Map<String, Object> response = new HashMap<>();
			response.put("mensaje", "Error: no se encontró el flag con ID " + id);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		try {

			Set<Jokes> jokesAsociados = new HashSet<>(flag.getJokeses());
			// Eliminar la asociación en ambos lados
			for (Jokes joke : jokesAsociados) {
				joke.getFlagses().remove(flag); 
				// Quita la flag del joke
			}
			flag.getJokeses().clear(); 
			// Limpia la colección en la flag

			// Guarda la flag actualizada
			flagsService.save(flag);

			Map<String, Object> response = new HashMap<>();
			response.put("mensaje", "Los chistes han sido desvinculados del flag con éxito");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, Object> response = new HashMap<>();
			response.put("mensaje", "Error al desvincular los chistes del flag en la base de datos");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Crea un nuevo flag.
	 * @param flag
	 * @param result
	 * @return
	 */
	@PostMapping("/nuevo")
	public ResponseEntity<?> create(@Valid @RequestBody Flags flag, BindingResult result) {
		Map<String, Object> response = new HashMap<>();

		if (flag.getId() != 0) {
			response.put("mensaje", "No debes proporcionar un ID. El sistema lo generará automáticamente.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		Flags existingFlag = flagsService.findByFlagIgnoreCase(flag.getFlag());
		if (existingFlag != null) {
			response.put("mensaje", "El flag con el nombre '" + flag.getFlag() + "' ya existe. Usa otro nombre.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			Flags flagNuevo = flagsService.save(flag);

			response.put("mensaje", "El flag ha sido creado con éxito!");
			response.put("flag", flagNuevo);
			return new ResponseEntity<>(response, HttpStatus.CREATED);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos.");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Obtener la lista de jokes e idioma usando el DTO
	@GetMapping("/{flagId}/jokes")
	public List<FlagJokeDto> getFlagJokes(@PathVariable int flagId) {
		return jokesService.findJokesByFlagId(flagId);
	}

	@DeleteMapping("/borrarConJokes/{id}")
	public ResponseEntity<?> deleteFlagAndJokes(@PathVariable int id) {
		Map<String, Object> response = new HashMap<>();
		Flags flag = flagsService.findById(id);

		if (flag == null) {
			response.put("mensaje", "El flag con ID " + id + " no existe.");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		try {
			// Suponiendo que deseas eliminar todos los jokes asociados a esta flag.
			Set<Jokes> jokesAsociados = new HashSet<>(flag.getJokeses());
			for (Jokes joke : jokesAsociados) {
				jokesService.delete(joke);
			}

			// Ahora eliminar la flag.
			flagsService.delete(flag);

			response.put("mensaje", "Flag y sus jokes asociados han sido eliminados.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("mensaje", "Error al eliminar flag y sus jokes.");
			response.put("error", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
