package com.universidad.repository;

import com.universidad.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    /**
     * Verifica si existe una inscripción para un estudiante y una materia específicos.
     * @param estudianteId El ID del estudiante.
     * @param materiaId El ID de la materia.
     * @return true si existe una inscripción, false de lo contrario.
     */
    boolean existsByEstudianteIdAndMateriaId(Long estudianteId, Long materiaId);

    /**
     * Busca inscripciones por el ID del estudiante.
     * @param estudianteId El ID del estudiante.
     * @return Una lista de inscripciones del estudiante.
     */
    List<Inscripcion> findByEstudianteId(Long estudianteId);

    /**
     * Busca inscripciones por el ID de la materia.
     * @param materiaId El ID de la materia.
     * @return Una lista de inscripciones de la materia.
     */
    List<Inscripcion> findByMateriaId(Long materiaId);
}