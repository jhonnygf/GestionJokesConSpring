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

import com.jonathangf.gestion.spring.jokes.mvc.dto.TypesDto;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Types;
import com.jonathangf.gestion.spring.jokes.mvc.services.IJokesService;
import com.jonathangf.gestion.spring.jokes.mvc.services.ITypesService;

import jakarta.validation.Valid;

/**
 * Controlador REST para la entidad Types.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE, RequestMethod.OPTIONS })
@RestController
@RequestMapping("/api/types")
public class TypesRestController {

	@Autowired
	private ITypesService typesService;

	@Autowired
	private IJokesService jokesService;

	/**
	 * Devuelve una lista de todos los tipos.
	 * @return
	 */
	@GetMapping({ "/", "" })
	public List<TypesDto> listarTipos() {
		List<Types> tipos = typesService.findAll();
		return tipos.stream().map(TypesDto::new).collect(Collectors.toList());
	}

	/**
	 * Devuelve un tipo por su id.
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> dto(@PathVariable int id) {
		Types type = null;
		Map<String, Object> respuesta = new HashMap<String, Object>();
		TypesDto typeDto = new TypesDto();

		try {
			type = typesService.findById(id);
		} catch (Exception e) {
			respuesta.put("mensaje", "Error al realizar la consulta en la base de datos");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getStackTrace().toString()));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (type == null) { 
			respuesta.put("mensaje",
					"El tipo con el id ".concat(Integer.toString(id)).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
		}
		typeDto = new TypesDto(type);
		return new ResponseEntity<TypesDto>(typeDto, HttpStatus.OK);
	}

	/**
	 * Elimina un tipo y sus chistes asociados
	 * @param id
	 * @return
	 */
	@DeleteMapping("/borrar/{id}")
	public ResponseEntity<?> eliminarTipo(@PathVariable("id") Integer id) {
		Map<String, Object> respuesta = new HashMap<>();

		Types type = typesService.findById(id);
		if (type == null) {
			respuesta.put("mensaje", "El tipo con el id " + id + " no existe en la base de datos");
			return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
		}

		try {
			if (type.getJokeses() != null && !type.getJokeses().isEmpty()) {
				jokesService.deleteByTypeId(id);
				type.getJokeses().clear();
			}
			typesService.deleteById(id);
			respuesta.put("mensaje", "El tipo y sus chistes asociados han sido eliminados con éxito");
			return new ResponseEntity<>(respuesta, HttpStatus.OK);
		} catch (DataAccessException e) {
			respuesta.put("mensaje", "Error al eliminar el tipo");
			respuesta.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Crea un nuevo tipo.
	 * @param tipo
	 * @param result
	 * @return
	 */
	@PostMapping("/nuevo")
	public ResponseEntity<?> crearTipo(@Valid @RequestBody Types tipo, BindingResult result) {
		Map<String, Object> response = new HashMap<>();

		if (tipo.getId() != 0) {
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

		Types existingType = typesService.findByTypeIgnoreCase(tipo.getType());
		if (existingType != null) {
			response.put("mensaje", "El tipo con el nombre '" + tipo.getType() + "' ya existe.");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			Types nuevoTipo = typesService.save(tipo);
			response.put("mensaje", "El tipo ha sido creado con éxito.");
			response.put("tipo", nuevoTipo);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al guardar el tipo en la base de datos.");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Actualiza un tipo.
	 * @param id
	 * @param tipo
	 * @param result
	 * @return
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<?> actualizarTipo(@PathVariable("id") Integer id, @Valid @RequestBody Types tipo,
			BindingResult result) {

		Map<String, Object> response = new HashMap<>();

		// Validación del request body
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errores);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		if (tipo.getId() == 0) {
			tipo.setId(id);
		} else if (tipo.getId() != id) {
			response.put("mensaje", "El id del type no coincide con el de la URL");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			Types tipoActualizado = typesService.save(tipo);
			response.put("mensaje", "El tipo ha sido actualizado con éxito.");
			response.put("tipo", new TypesDto(tipoActualizado));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el tipo en la base de datos.");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}