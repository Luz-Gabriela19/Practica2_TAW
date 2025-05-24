package com.universidad.validation;

import com.universidad.repository.EstudianteRepository;
import com.universidad.repository.InscripcionRepository;
import com.universidad.repository.MateriaRepository;
import org.springframework.stereotype.Component;

@Component
public class InscripcionValidator {

    private final InscripcionRepository inscripcionRepository;
    private final EstudianteRepository estudianteRepository;
    private final MateriaRepository materiaRepository;

    public InscripcionValidator(InscripcionRepository inscripcionRepository,
                                EstudianteRepository estudianteRepository,
                                MateriaRepository materiaRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.estudianteRepository = estudianteRepository;
        this.materiaRepository = materiaRepository;
    }

    /**
     * Valida si un estudiante ya está inscrito en una materia específica.
     * @param estudianteId ID del estudiante.
     * @param materiaId ID de la materia.
     * @throws BusinessException si el estudiante ya está inscrito en la materia.
     */
    public void validarInscripcionDuplicada(Long estudianteId, Long materiaId) {
        if (inscripcionRepository.existsByEstudianteIdAndMateriaId(estudianteId, materiaId)) {
            throw new BusinessException("El estudiante con ID " + estudianteId + " ya está inscrito en la materia con ID " + materiaId);
        }
    }

    /**
     * Valida la existencia del estudiante y la materia antes de la inscripción.
     * @param estudianteId ID del estudiante.
     * @param materiaId ID de la materia.
     * @throws BusinessException si el estudiante o la materia no existen.
     */
    public void validarExistenciaEstudianteYMateria(Long estudianteId, Long materiaId) {
        if (!estudianteRepository.existsById(estudianteId)) {
            throw new BusinessException("El estudiante con ID " + estudianteId + " no existe.");
        }
        if (!materiaRepository.existsById(materiaId)) {
            throw new BusinessException("La materia con ID " + materiaId + " no existe.");
        }
    }

    /**
     * Realiza una validación completa para la creación de una inscripción.
     * @param estudianteId ID del estudiante.
     * @param materiaId ID de la materia.
     */
    public void validarInscripcionParaCreacion(Long estudianteId, Long materiaId) {
        validarExistenciaEstudianteYMateria(estudianteId, materiaId);
        validarInscripcionDuplicada(estudianteId, materiaId);
    }

    // Clase de excepción personalizada para errores de negocio
    public static class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }
}