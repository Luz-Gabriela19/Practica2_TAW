package com.universidad.service.impl;

import com.universidad.dto.InscripcionDTO;
import com.universidad.model.Estudiante;
import com.universidad.model.Inscripcion;
import com.universidad.model.Materia;
import com.universidad.repository.EstudianteRepository;
import com.universidad.repository.InscripcionRepository;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IInscripcionService;
import com.universidad.validation.InscripcionValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscripcionServiceImpl implements IInscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository; // Necesario para buscar el estudiante
    
    @Autowired
    private MateriaRepository materiaRepository; // Necesario para buscar la materia

    @Autowired
    private InscripcionValidator inscripcionValidator;

    // Constructor para inyección de dependencias (recomendado)
    public InscripcionServiceImpl(InscripcionRepository inscripcionRepository,
                                  EstudianteRepository estudianteRepository,
                                  MateriaRepository materiaRepository,
                                  InscripcionValidator inscripcionValidator) {
        this.inscripcionRepository = inscripcionRepository;
        this.estudianteRepository = estudianteRepository;
        this.materiaRepository = materiaRepository;
        this.inscripcionValidator = inscripcionValidator;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionDTO> obtenerTodasLasInscripciones() {
        return inscripcionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public InscripcionDTO obtenerInscripcionPorId(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada con ID: " + id));
        return convertToDTO(inscripcion);
    }

    @Override
    @Transactional
    public InscripcionDTO crearInscripcion(InscripcionDTO inscripcionDTO) {
        // Validaciones de negocio antes de crear
        inscripcionValidator.validarInscripcionParaCreacion(inscripcionDTO.getEstudianteId(), inscripcionDTO.getMateriaId());

        Estudiante estudiante = estudianteRepository.findById(inscripcionDTO.getEstudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + inscripcionDTO.getEstudianteId()));

        Materia materia = materiaRepository.findById(inscripcionDTO.getMateriaId())
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + inscripcionDTO.getMateriaId()));

        Inscripcion inscripcion = convertToEntity(inscripcionDTO);
        inscripcion.setEstudiante(estudiante); // Asigna el objeto Estudiante
        inscripcion.setMateria(materia);     // Asigna el objeto Materia

        Inscripcion inscripcionGuardada = inscripcionRepository.save(inscripcion);
        return convertToDTO(inscripcionGuardada);
    }

    @Override
    @Transactional
    public InscripcionDTO actualizarInscripcion(Long id, InscripcionDTO inscripcionDTO) {
        Inscripcion inscripcionExistente = inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada con ID: " + id));
        
        // Aquí puedes agregar validaciones adicionales si es necesario al actualizar
        // Por ejemplo, si se permite cambiar de estudiante o materia, validar que existan.
        if (!inscripcionExistente.getEstudiante().getId().equals(inscripcionDTO.getEstudianteId())) {
             Estudiante nuevoEstudiante = estudianteRepository.findById(inscripcionDTO.getEstudianteId())
                .orElseThrow(() -> new RuntimeException("Nuevo Estudiante no encontrado con ID: " + inscripcionDTO.getEstudianteId()));
             inscripcionExistente.setEstudiante(nuevoEstudiante);
        }

        if (!inscripcionExistente.getMateria().getId().equals(inscripcionDTO.getMateriaId())) {
            Materia nuevaMateria = materiaRepository.findById(inscripcionDTO.getMateriaId())
                .orElseThrow(() -> new RuntimeException("Nueva Materia no encontrada con ID: " + inscripcionDTO.getMateriaId()));
            inscripcionExistente.setMateria(nuevaMateria);
        }

        inscripcionExistente.setFechaInscripcion(inscripcionDTO.getFechaInscripcion());

        Inscripcion inscripcionActualizada = inscripcionRepository.save(inscripcionExistente);
        return convertToDTO(inscripcionActualizada);
    }

    @Override
    @Transactional
    public void eliminarInscripcion(Long id) {
        if (!inscripcionRepository.existsById(id)) {
            throw new RuntimeException("Inscripción no encontrada con ID: " + id);
        }
        inscripcionRepository.deleteById(id);
    }

    // Métodos auxiliares para conversión entre DTO y Entidad
    private InscripcionDTO convertToDTO(Inscripcion inscripcion) {
        return InscripcionDTO.builder()
                .id(inscripcion.getId())
                .estudianteId(inscripcion.getEstudiante().getId())
                .materiaId(inscripcion.getMateria().getId())
                .fechaInscripcion(inscripcion.getFechaInscripcion())
                .build();
    }

    private Inscripcion convertToEntity(InscripcionDTO inscripcionDTO) {
        // En la conversión a entidad para guardar, el estudiante y materia se setearán
        // después de recuperarlos de la base de datos en el método crearInscripcion/actualizarInscripcion.
        // Aquí solo construimos la entidad base.
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setId(inscripcionDTO.getId()); // ID puede ser nulo para nuevas inscripciones
        inscripcion.setFechaInscripcion(inscripcionDTO.getFechaInscripcion());
        return inscripcion;
    }
}