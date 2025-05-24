package com.universidad.controller;

import com.universidad.dto.InscripcionDTO;
import com.universidad.service.IInscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid; // Para las validaciones del DTO

import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    @Autowired
    private IInscripcionService inscripcionService;

    // Obtener todas las inscripciones
    @GetMapping
    public ResponseEntity<List<InscripcionDTO>> obtenerTodasLasInscripciones() {
        List<InscripcionDTO> inscripciones = inscripcionService.obtenerTodasLasInscripciones();
        return new ResponseEntity<>(inscripciones, HttpStatus.OK);
    }

    // Obtener una inscripción por ID
    @GetMapping("/{id}")
    public ResponseEntity<InscripcionDTO> obtenerInscripcionPorId(@PathVariable Long id) {
        try {
            InscripcionDTO inscripcion = inscripcionService.obtenerInscripcionPorId(id);
            return new ResponseEntity<>(inscripcion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear una nueva inscripción
    @PostMapping
    public ResponseEntity<InscripcionDTO> crearInscripcion(@Valid @RequestBody InscripcionDTO inscripcionDTO) {
        try {
            InscripcionDTO nuevaInscripcion = inscripcionService.crearInscripcion(inscripcionDTO);
            return new ResponseEntity<>(nuevaInscripcion, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Aquí puedes manejar excepciones más específicas (ej. validaciones, estudiante/materia no encontrados)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Actualizar una inscripción existente
    @PutMapping("/{id}")
    public ResponseEntity<InscripcionDTO> actualizarInscripcion(@PathVariable Long id,
                                                                 @Valid @RequestBody InscripcionDTO inscripcionDTO) {
        try {
            InscripcionDTO inscripcionActualizada = inscripcionService.actualizarInscripcion(id, inscripcionDTO);
            return new ResponseEntity<>(inscripcionActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // O HttpStatus.BAD_REQUEST si la validación falla
        }
    }

    // Eliminar una inscripción
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInscripcion(@PathVariable Long id) {
        try {
            inscripcionService.eliminarInscripcion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}