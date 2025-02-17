package com.jonathangf.gestion.spring.jokes.mvc.controller;

import java.util.Comparator;
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
import com.jonathangf.gestion.spring.jokes.mvc.dto.CategoriesDto;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Categories;
import com.jonathangf.gestion.spring.jokes.mvc.services.ICategoriesService;
import com.jonathangf.gestion.spring.jokes.mvc.services.IJokesService;

import jakarta.validation.Valid;
/**
 * Controlador REST para las categorías de chistes.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/categories")
public class CategoriesRestController {

    @Autowired
    private ICategoriesService categoriesService;
    
    @Autowired
    private IJokesService jokesService;

	/**
	 * 🔹 Listar todas las categorías
	 */
    @GetMapping({ "/", "" })
    public List<CategoriesDto> listarDto() {
        List<Categories> categories = categoriesService.findAll();
        categories.sort(Comparator.comparingInt(Categories::getId));
        return categories.stream().map(CategoriesDto::new).collect(Collectors.toList());
    }


    /**
     * 🔹 Obtener una categoría por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCategoria(@PathVariable int id) {
        Categories categoria = categoriesService.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (categoria == null) {
            response.put("mensaje", "La categoría con ID " + id + " no existe en la base de datos.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        CategoriesDto dto = new CategoriesDto(categoria);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    /**
     * 🔹 Crear una nueva categoría
     */
    @PostMapping("/nuevo")
    public ResponseEntity<?> crearCategoria(@Valid @RequestBody Categories categoria, BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if (categoria.getId() != 0) {
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

        Categories existingCategory = categoriesService.findByCategoryIgnoreCase(categoria.getCategory());
        if (existingCategory != null) {
            response.put("mensaje", "La categoría con el nombre '" + categoria.getCategory() + "' ya existe.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            Categories nuevaCategoria = categoriesService.save(categoria);
            response.put("mensaje", "La categoría ha sido creada con éxito.");
            response.put("categoria", nuevaCategoria);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al guardar la categoría en la base de datos.");
            response.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 🔹 Editar una categoría existente
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizarCategoria(@Valid @RequestBody Categories categoria, BindingResult result, @PathVariable int id) {
        Categories categoriaActual = categoriesService.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (categoriaActual == null) {
            response.put("mensaje", "Error: no se pudo editar, la categoría con ID " + id + " no existe.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            categoriaActual.setCategory(categoria.getCategory()); 
            Categories categoriaActualizada = categoriesService.save(categoriaActual);
            CategoriesDto categoriaDto = new CategoriesDto(categoriaActualizada);

            response.put("mensaje", "La categoría ha sido actualizada con éxito.");
            response.put("categoria", categoriaDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar la categoría en la base de datos.");
            response.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 🔹 Eliminar una category por su ID
     * @param id
     * @return
     */
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable int id) {
        Categories categoria = categoriesService.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (categoria == null) {
            response.put("mensaje", "La categoría con ID " + id + " no existe.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            // Borrar los chistes de esta categoría
            jokesService.deleteByCategoryId(id); 

            //borrar la categoría
            categoriesService.deleteById(id);

            response.put("mensaje", "Categoría y chistes eliminados con éxito.");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar la categoría de la base de datos.");
            response.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
