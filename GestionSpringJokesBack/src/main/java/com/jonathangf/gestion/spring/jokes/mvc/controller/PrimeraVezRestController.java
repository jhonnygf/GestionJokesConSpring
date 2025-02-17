package com.jonathangf.gestion.spring.jokes.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

import com.jonathangf.gestion.spring.jokes.mvc.dto.PrimeraVezDto;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Jokes;
import com.jonathangf.gestion.spring.jokes.mvc.entity.PrimeraVez;
import com.jonathangf.gestion.spring.jokes.mvc.entity.Telefonos;
import com.jonathangf.gestion.spring.jokes.mvc.services.IJokesService;
import com.jonathangf.gestion.spring.jokes.mvc.services.IPrimeraVezService;

/**
 * Controlador REST para la entidad PrimeraVez.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/primera-vez")
public class PrimeraVezRestController {

    @Autowired
    private IPrimeraVezService pvService;

    @Autowired
    private IJokesService jokesService;  
    
     @PostMapping("/nuevo")
    public ResponseEntity<?> create(@RequestBody PrimeraVez pv) {
        Map<String, Object> response = new HashMap<>();

        if (pv.getJoke() == null || pv.getJoke().getId() <= 0) { 
            response.put("mensaje", "Error: El chiste es obligatorio.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Jokes joke = jokesService.findById(pv.getJoke().getId());
        if (joke == null) {
            response.put("mensaje", "No existe el chiste con ID: " + pv.getJoke().getId());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        pv.setJoke(joke);

        if (pvService.existsByJokeId(pv.getJoke().getId())) {
            response.put("mensaje", "Error: Ya existe un registro con este id_joke.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        for (Telefonos t : pv.getTelefonos()) {
            t.setPrimeraVez(pv);
        }

        try {

            PrimeraVez saved = pvService.save(pv);
            PrimeraVezDto responseDto = new PrimeraVezDto(
                saved.getId(),
                saved.getPrograma(),
                saved.getFechaEmision(),
                saved.getJoke().getId(),
                saved.getTelefonos().stream().map(Telefonos::getNumero).collect(Collectors.toList())
            );

            response.put("mensaje", "Registro creado exitosamente.");
            response.put("primeraVez", responseDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            response.put("mensaje", "Error de integridad de datos: ya existe un registro con el mismo id_joke.");
            response.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            response.put("mensaje", "Error inesperado al guardar el registro.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     @GetMapping("/")
     public ResponseEntity<List<PrimeraVezDto>> listAll() {
         List<PrimeraVezDto> lista = pvService.findAll().stream().map(pv -> new PrimeraVezDto(
             pv.getId(),
             pv.getPrograma(),
             pv.getFechaEmision(),
             pv.getJoke().getId(),
             pv.getTelefonos().stream().map(Telefonos::getNumero).collect(Collectors.toList()) // Teléfonos
         )).collect(Collectors.toList());

         return ResponseEntity.ok(lista);
     }

     /**
      * Borrar un registro de PrimeraVez y sus teléfonos asociados.
      * @param id
      * @return
      */
     @DeleteMapping("/borrar/{id}")
     @Transactional
     public ResponseEntity<?> delete(@PathVariable int id) {
    	 Map<String, String> resp = new HashMap<>();
         PrimeraVez actual = pvService.findById(id);
         if (actual == null) {
             return ResponseEntity.notFound().build();
         }
         
         // Si la entidad Jokes tiene una relación inversa a PrimeraVez
         if (actual.getJoke() != null) {
             actual.getJoke().setPrimeraVez(null);
         }
         
         // Forzar la carga de la colección de teléfonos 
         actual.getTelefonos().size();
         actual.getTelefonos().clear();
         pvService.delete(actual);
         
         resp.put("mensaje", "PrimeraVez y sus teléfonos han sido borrados con éxito");
         return ResponseEntity.ok(resp);

     }

     /**
      * Actualizar un registro de PrimeraVez y sus teléfonos asoci
      * @param pv
      * @param id
      * @return
      */
     @PutMapping("/update/{id}")
     public ResponseEntity<?> update(@RequestBody PrimeraVez pv, @PathVariable int id) {
         PrimeraVez actual = pvService.findById(id);
         if (actual == null) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body(Map.of("mensaje", "No se encontró el registro con ID: " + id));
         }

         // Actualizar datos básicos
         actual.setPrograma(pv.getPrograma());
         actual.setFechaEmision(pv.getFechaEmision());

         // Limpia la colección gestionada.
         actual.getTelefonos().clear();

         if (pv.getTelefonos() != null && !pv.getTelefonos().isEmpty()) {
             // En lugar de reusar directamente los objetos recibidos,
             // se crean nuevos objetos de Telefonos para evitar conflictos de estado.
             for (Telefonos t : pv.getTelefonos()) {
                 Telefonos nuevoTel = new Telefonos();
                 nuevoTel.setNumero(t.getNumero());
                 nuevoTel.setPrimeraVez(actual);
                 actual.getTelefonos().add(nuevoTel);
             }
         }
         
         PrimeraVez updated = pvService.save(actual);

         PrimeraVezDto responseDto = new PrimeraVezDto(
             updated.getId(),
             updated.getPrograma(),
             updated.getFechaEmision(),
             updated.getJoke().getId(),
             updated.getTelefonos().stream().map(Telefonos::getNumero).collect(Collectors.toList())
         );

         return ResponseEntity.ok(Map.of("mensaje", "Registro actualizado con éxito", "primeraVez", responseDto));
     }
}