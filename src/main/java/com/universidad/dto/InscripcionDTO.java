package com.universidad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

/**
 * DTO que representa los datos de una inscripción para transferencia entre capas.
 * Incluye validaciones para los campos principales como ID de estudiante, ID de materia y fecha de inscripción.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscripcionDTO implements Serializable {
    private Long id; // ID de la inscripción (opcional para creación, necesario para actualización/eliminación)

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long estudianteId; // ID del estudiante involucrado en la inscripción

    @NotNull(message = "El ID de la materia es obligatorio")
    private Long materiaId; // ID de la materia involucrada en la inscripción

    @NotNull(message = "La fecha de inscripción es obligatoria")
    @PastOrPresent(message = "La fecha de inscripción no puede ser futura")
    private LocalDate fechaInscripcion; // Fecha en que se realizó la inscripción
}