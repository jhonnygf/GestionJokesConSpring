package com.jonathangf.gestion.spring.jokes.mvc.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jonathangf.gestion.spring.jokes.mvc.dto.JokesDto;
import com.jonathangf.gestion.spring.jokes.mvc.dto.JokesDtoConPrimera;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Categories;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Flags;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Jokes;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Language;
import com.jonathangf.gestion.spring.jokes.mvc.entity.PrimeraVez;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Telefonos;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Types;
import com.jonathangf.gestion.spring.jokes.mvc.services.ICategoriesService;
import com.jonathangf.gestion.spring.jokes.mvc.services.IFlagsService;
import com.jonathangf.gestion.spring.jokes.mvc.services.IJokesService;
import com.jonathangf.gestion.spring.jokes.mvc.services.ILanguageService;
import com.jonathangf.gestion.spring.jokes.mvc.services.IPrimeraVezService;
import com.jonathangf.gestion.spring.jokes.mvc.services.ITypesService;

import jakarta.validation.Valid;


//Esto hace que se puedan conectar desde todas las url, cualquier programa desde cualquier sitio

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})@RestController	
@RequestMapping("/api/jokes")
public class JokesRestController {
	
	@Autowired
	private IJokesService jokesService;
	@Autowired
	private ICategoriesService categoriesService;
	@Autowired
	private ILanguageService languageService;
	@Autowired
	private IFlagsService flagsService;
	
	@Autowired
	private ITypesService typesService; // para cargar el tipo (si existe)
	
    private IPrimeraVezService primeraVezService; // para cargar la primeraVez (si existe)

	@GetMapping({"/", ""})
	public List<JokesDto> listarDto() {
	    List<Jokes> jokes = jokesService.findAll();
	    List<JokesDto> listaJokesDto = new ArrayList<>();

	    jokes.forEach(joke -> {
	        // Verificar si la categoría es nula
	        String category = (joke.getCategories() != null) ? joke.getCategories().getCategory() : "Sin categoría";

	        // Verificar si el idioma es nulo
	        String language = (joke.getLanguage() != null) ? joke.getLanguage().getLanguage() : "Idioma no especificado";

	        // Extraer los Flags como una lista de Strings
	        Set<String> flags = joke.getFlagses().stream()
	                                .map(flag -> flag.getFlag()) // Asegúrate de que `getFlag()` devuelve el valor deseado
	                                .collect(Collectors.toSet());

	        // Crear el DTO con los valores adecuados
	        JokesDto jokeDto = new JokesDto();
	        jokeDto.setId(joke.getId());
	        jokeDto.setText1(joke.getText1());
	        jokeDto.setText2(joke.getText2());
	        jokeDto.setCategory(category);
	        jokeDto.setLanguage(language);
	        jokeDto.setFlagses(flags); // Asignar los flags directamente al DTO

	        listaJokesDto.add(jokeDto);
	    });

	    return listaJokesDto;
	}


	
	@GetMapping("/{id}")
	public ResponseEntity<?> dto(@PathVariable int id) {
     Jokes joke = null;
     Map<String, Object> respuesta = new HashMap<String,Object>();
     JokesDto jokeDto = new JokesDto();
     
     try {
			joke = jokesService.findById(id);			
		} catch (Exception e) { //Base de datos inaccesible
			respuesta.put("mensaje", "Error al realizar la consulta en la base de datos");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getStackTrace().toString()));
			return new ResponseEntity<Map<String,Object>>(respuesta,HttpStatus.INTERNAL_SERVER_ERROR);
     }
     if(joke == null) {  // Hemos buscado un chiste que no existe
			respuesta.put("mensaje",
					"El chiste con el id ".concat(Integer.toString(id)).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
     }
     jokeDto.setId(joke.getId());
     jokeDto.setText1(joke.getText1());
     jokeDto.setText2(joke.getText2());
     jokeDto.setCategory(joke.getCategories().getCategory());
     jokeDto.setLanguage(joke.getLanguage().getLanguage());
     jokeDto.setFlagses(joke.getFlagses().stream().map(Flags::getFlag).collect(Collectors.toSet()));
     return new ResponseEntity<JokesDto>(jokeDto,HttpStatus.OK);
	}
	
	@PostMapping({"/nuevo", "/nuevo/"})
	public ResponseEntity<?> crearJoke(@Valid @RequestBody Jokes joke, BindingResult result) {
	    Map<String, Object> response = new HashMap<>();

	    // Validación de errores
	    if (result.hasErrors()) {
	        List<String> errors = result.getFieldErrors().stream()
	                .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
	                .toList();
	        response.put("errors", errors);
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    try {
	        // ✅ 1. Validar y obtener la categoría desde la BD si se envió una
	        if (joke.getCategories() != null && joke.getCategories().getId() != 0) {
	            Categories categoriaBD = categoriesService.findById(joke.getCategories().getId());
	            if (categoriaBD == null) {
	                response.put("mensaje", "La categoría con ID " + joke.getCategories().getId() + " no existe.");
	                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	            }
	            joke.setCategories(categoriaBD);
	        }

	        // ✅ 2. Validar y obtener el idioma desde la BD si se envió
	        if (joke.getLanguage() != null && joke.getLanguage().getId() != 0) {
	            Language languageBD = languageService.findById(joke.getLanguage().getId());
	            if (languageBD == null) {
	                response.put("mensaje", "El idioma con ID " + joke.getLanguage().getId() + " no existe.");
	                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	            }
	            joke.setLanguage(languageBD);
	        }

	        // ✅ 3. Buscar el tipo en la BD según el contenido del chiste
	        if (joke.getText1() != null && joke.getText2() == null) {
	            Types singleType = typesService.findByTypeIgnoreCase("Single");
	            if (singleType == null) {
	                response.put("mensaje", "El tipo 'Single' no existe en la base de datos.");
	                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	            }
	            joke.setTypes(singleType);
	        } else if (joke.getText1() != null && joke.getText2() != null) {
	            Types twoPartType = typesService.findByTypeIgnoreCase("Twopart");
	            if (twoPartType == null) {
	                response.put("mensaje", "El tipo 'Twopart' no existe en la base de datos.");
	                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	            }
	            joke.setTypes(twoPartType);
	        }

	        // ✅ 4. Validar y obtener los Flags desde la BD si se enviaron
	        if (joke.getFlagses() != null && !joke.getFlagses().isEmpty()) {
	            Set<Flags> flagsBD = joke.getFlagses().stream()
	                .map(f -> flagsService.findById(f.getId()))
	                .filter(f -> f != null)
	                .collect(Collectors.toSet());
	            joke.setFlagses(flagsBD);
	        }

	        // ✅ 5. Guardar el chiste
	        Jokes jokeNuevo = jokesService.save(joke);

	        // ✅ 6. Convertir a DTO antes de devolver
	        JokesDto jokeDto = new JokesDto(
	                jokeNuevo.getId(),
	                jokeNuevo.getText1(),
	                jokeNuevo.getText2(),
	                jokeNuevo.getCategories() != null ? jokeNuevo.getCategories().getCategory() : "Sin categoría",
	                jokeNuevo.getLanguage() != null ? jokeNuevo.getLanguage().getLanguage() : "Sin idioma",
	                jokeNuevo.getFlagses().stream().map(Flags::getFlag).collect(Collectors.toSet())
	        );

	        response.put("mensaje", "El chiste ha sido creado con éxito!");
	        response.put("joke", jokeDto); // ✅ DEVOLVEMOS UN DTO, NO LA ENTIDAD COMPLETA
	        return new ResponseEntity<>(response, HttpStatus.CREATED);

	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al realizar el insert en la base de datos");
	        response.put("error", e.getMostSpecificCause().getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	
	@DeleteMapping({"/borrar/{id}", "/borrar/{id}/"})
	public ResponseEntity<?> delete(@PathVariable int id) {
		Jokes jokeBorrado = jokesService.findById(id);
		Map<String, Object> response = new HashMap<>();

		 if (jokeBorrado == null) {
			 
		        response.put("mensaje", "El joke ID: ".concat(String.valueOf(id)).concat(" no existe en la base de datos!"));
		        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		    }
		try {
			jokesService.deleteById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el joke de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El joke ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Jokes joke, BindingResult result, @PathVariable int id ) {
	    Jokes jokeActual = jokesService.findById(id);
	    Map<String, Object> response = new HashMap<>();

	    if (jokeActual == null) {
	        response.put("mensaje", "Error: no se pudo editar, el joke ID " + id + " no existe en la base de datos!");
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }

	    try {
	        // 1️⃣ Actualizar campos básicos
	        jokeActual.setText1(joke.getText1());
	        jokeActual.setText2(joke.getText2());

	        // 2️⃣ Cargar categoría desde BD
	        if (joke.getCategories() != null && joke.getCategories().getId() != 0) {
	            Categories catBD = categoriesService.findById(joke.getCategories().getId());
	            jokeActual.setCategories(catBD);
	        } else {
	            jokeActual.setCategories(null);
	        }

	        // 3️⃣ Cargar lenguaje desde BD
	        if (joke.getLanguage() != null && joke.getLanguage().getId() != 0) {
	            Language langBD = languageService.findById(joke.getLanguage().getId());
	            jokeActual.setLanguage(langBD);
	        } else {
	            jokeActual.setLanguage(null);
	        }

	        // 4️⃣ Buscar el tipo en la BD en lugar de asignarlo directamente
	        if (joke.getText1() != null && joke.getText2() == null) {
	            Types singleType = typesService.findByTypeIgnoreCase("Single");
	            jokeActual.setTypes(singleType);
	        } else if (joke.getText1() != null && joke.getText2() != null) {
	            Types twoPartType = typesService.findByTypeIgnoreCase("Twopart");
	            jokeActual.setTypes(twoPartType);
	        }

	        // 5️⃣ Actualizar los flags
	        jokeActual.getFlagses().clear();
	        for (Flags f : joke.getFlagses()) {
	            Flags fBD = flagsService.findById(f.getId());
	            if (fBD != null) {
	                jokeActual.getFlagses().add(fBD);
	            }
	        }

	        // 6️⃣ Guardar el objeto actualizado
	        Jokes jokeUpdated = jokesService.save(jokeActual);

	        // 7️⃣ **Convertir el objeto actualizado en un DTO antes de devolverlo**
	        JokesDto jokeDto = new JokesDto(
	            jokeUpdated.getId(),
	            jokeUpdated.getText1(),
	            jokeUpdated.getText2(),
	            jokeUpdated.getCategories() != null ? jokeUpdated.getCategories().getCategory() : "Sin categoría",
	            jokeUpdated.getLanguage() != null ? jokeUpdated.getLanguage().getLanguage() : "Sin idioma",
	            jokeUpdated.getFlagses().stream().map(Flags::getFlag).collect(Collectors.toSet())
	        );

	        response.put("mensaje", "El joke ha sido actualizado con éxito!");
	        response.put("joke", jokeDto); // ✅ Solo devolvemos el DTO, evitando la recursividad
	        return new ResponseEntity<>(response, HttpStatus.OK);

	    } catch (DataAccessException e) {
	        response.put("mensaje", "Error al actualizar el joke en la base de datos");
	        response.put("error", e.getMostSpecificCause().getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@GetMapping("/conPrimeraVez")
	public List<JokesDtoConPrimera> getAllJokesConPrimera() {
	    List<Jokes> jokes = jokesService.findAll();
	    List<JokesDtoConPrimera> lista = new ArrayList<>();

	    for (Jokes j : jokes) {
	        JokesDtoConPrimera dto = new JokesDtoConPrimera(
	            j.getId(),
	            j.getText1(),
	            j.getText2(),
	            j.getCategories() != null ? j.getCategories().getCategory() : "Sin categoría",
	            j.getLanguage() != null ? j.getLanguage().getLanguage() : "Idioma no especificado",
	            j.getFlagses().stream().map(Flags::getFlag).collect(Collectors.toSet()),
	            false,  // Default en caso de no tener PrimeraVez
	            null,
	            null,
	            new ArrayList<>()
	        );

	        // Buscar 'PrimeraVez' y mapear datos si existe
	        PrimeraVez pv = primeraVezService.findByJokeId(j.getId());
	        if (pv != null) {
	            dto.setHasPrimeraVez(true);
	            dto.setPrograma(pv.getPrograma());
	            dto.setFechaEmision(pv.getFechaEmision());
	            dto.setTelefonos(
	                pv.getTelefonos().stream()
	                    .map(Telefonos::getNumero)
	                    .collect(Collectors.toList())
	            );
	        }

	        lista.add(dto);
	    }
	    return lista;
	}


    // 7) Búsqueda filtrada por text1 (no sensible a mayúsculas)
	@GetMapping("/buscar")
	public List<JokesDto> buscar(@RequestParam("texto") String texto) {
	    List<Jokes> jokes = jokesService.filtrarPorTexto(texto);
	    return jokes.stream().map(j ->
	        new JokesDto(
	            j.getId(),
	            j.getText1(),
	            j.getText2(),
	            j.getCategories() != null ? j.getCategories().getCategory() : "Sin categoría",
	            j.getLanguage() != null ? j.getLanguage().getLanguage() : "Idioma no especificado",
	            j.getFlagses().stream().map(Flags::getFlag).collect(Collectors.toSet())
	        )
	    ).collect(Collectors.toList());
	}


    // 8) Listar Jokes que NO tienen primeraVez
	@GetMapping("/sinPrimeraVez")
	public List<JokesDto> jokesSinPrimeraVez() {
	    List<Jokes> jokes = jokesService.findJokesSinPrimeraVez();
	    return jokes.stream().map(j ->
	        new JokesDto(
	            j.getId(),
	            j.getText1(),
	            j.getText2(),
	            j.getCategories() != null ? j.getCategories().getCategory() : "Sin categoría",
	            j.getLanguage() != null ? j.getLanguage().getLanguage() : "Idioma no especificado",
	            j.getFlagses().stream().map(Flags::getFlag).collect(Collectors.toSet())
	        )
	    ).collect(Collectors.toList());
	}

	@DeleteMapping("/borrarConJokes/{id}")
	public ResponseEntity<?> deleteFlagAndJokes(@PathVariable int id) {
	    Map<String, Object> response = new HashMap<>();
	    Flags flag = flagsService.findById(id);
	    
	    if (flag == null) {
	        response.put("mensaje", "El flag con ID " + id + " no existe en la base de datos.");
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	    
	    try {
	        // 1. Eliminar los jokes asociados a esta flag
	        // Nota: Si un joke está asociado a varias flags, deberás definir la regla de negocio.
	        // En este ejemplo, se eliminarán TODOS los jokes asociados.
	        Set<Jokes> jokesAsociados = new HashSet<>(flag.getJokeses());
	        for (Jokes joke : jokesAsociados) {
	            jokesService.delete(joke);
	        }
	        
	        // 2. Eliminar la flag
	        flagsService.delete(flag);
	        
	        response.put("mensaje", "Flag y sus jokes asociados han sido eliminados con éxito.");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        response.put("mensaje", "Error al eliminar flag y sus jokes asociados.");
	        response.put("error", e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	@GetMapping("/api/jokes-sin-primera-vez")
	public ResponseEntity<List<Jokes>> getJokesSinPrimeraVez() {
	    List<Jokes> jokesSinPrimeraVez = jokesService.findJokesWithoutPrimeraVez();
	    return ResponseEntity.ok(jokesSinPrimeraVez);
	}
	@GetMapping("/primera-vez/list")
	public ResponseEntity<List<JokesDtoConPrimera>> getJokesConPrimeraVez() {
	    List<Jokes> jokes = jokesService.findJokesConPrimeraVez();
	    List<JokesDtoConPrimera> listaDto = new ArrayList<>();
	    for (Jokes j : jokes) {
	        String categoria = (j.getCategories() != null) ? j.getCategories().getCategory() : "Sin categoría";
	        String idioma = (j.getLanguage() != null) ? j.getLanguage().getLanguage() : "Idioma no especificado";
	        // Se supone que j.getPrimeraVez() no es nulo, ya que la consulta lo filtra
	        PrimeraVez pv = j.getPrimeraVez();
	        JokesDtoConPrimera dto = new JokesDtoConPrimera(
	            j.getId(),
	            j.getText1(),
	            j.getText2(),
	            categoria,
	            idioma,
	            j.getFlagses().stream().map(f -> f.getFlag()).collect(Collectors.toSet()),
	            true,
	            pv.getPrograma(),
	            pv.getFechaEmision(),
	            pv.getTelefonos().stream().map(Telefonos::getNumero).collect(Collectors.toList())
	        );
	        listaDto.add(dto);
	    }
	    return ResponseEntity.ok(listaDto);
	}

	@GetMapping("/primera-vez/buscar")
	public ResponseEntity<List<JokesDtoConPrimera>> buscarJokesConPrimeraVez(
	        @RequestParam("texto") String texto) {

	    // Obtén todos los chistes con PrimeraVez
	    List<Jokes> jokes = jokesService.findJokesConPrimeraVez();

	    // Filtra por text1 (sin distinguir mayúsculas)
	    List<JokesDtoConPrimera> listaFiltrada = jokes.stream()
	        .filter(j -> j.getText1().toLowerCase().contains(texto.toLowerCase()))
	        .map(j -> {
	            String categoria = (j.getCategories() != null) ? j.getCategories().getCategory() : "Sin categoría";
	            String idioma = (j.getLanguage() != null) ? j.getLanguage().getLanguage() : "Idioma no especificado";
	            PrimeraVez pv = j.getPrimeraVez();
	            return new JokesDtoConPrimera(
	                j.getId(),
	                j.getText1(),
	                j.getText2(),
	                categoria,
	                idioma,
	                j.getFlagses().stream().map(flag -> flag.getFlag()).collect(Collectors.toSet()),
	                true,
	                pv.getPrograma(),
	                pv.getFechaEmision(),
	                pv.getTelefonos().stream().map(Telefonos::getNumero).collect(Collectors.toList())
	            );
	        })
	        .collect(Collectors.toList());

	    return ResponseEntity.ok(listaFiltrada);
	}
	

}
	

