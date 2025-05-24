package com.universidad.service;

import com.universidad.dto.InscripcionDTO;

import java.util.List;

public interface IInscripcionService {

    /**
     * Obtiene todas las inscripciones.
     * @return Una lista de InscripcionDTO.
     */
    List<InscripcionDTO> obtenerTodasLasInscripciones();

    /**
     * Obtiene una inscripción por su ID.
     * @param id El ID de la inscripción.
     * @return La InscripcionDTO encontrada.
     * @throws RuntimeException si la inscripción no es encontrada.
     */
    InscripcionDTO obtenerInscripcionPorId(Long id);

    /**
     * Crea una nueva inscripción.
     * @param inscripcionDTO El DTO de la inscripción a crear.
     * @return La InscripcionDTO creada.
     */
    InscripcionDTO crearInscripcion(InscripcionDTO inscripcionDTO);

    /**
     * Actualiza una inscripción existente.
     * @param id El ID de la inscripción a actualizar.
     * @param inscripcionDTO El DTO de la inscripción con los nuevos datos.
     * @return La InscripcionDTO actualizada.
     * @throws RuntimeException si la inscripción no es encontrada.
     */
    InscripcionDTO actualizarInscripcion(Long id, InscripcionDTO inscripcionDTO);

    /**
     * Elimina una inscripción por su ID.
     * @param id El ID de la inscripción a eliminar.
     * @throws RuntimeException si la inscripción no es encontrada.
     */
    void eliminarInscripcion(Long id);
}