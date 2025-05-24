package com.universidad.model;
import java.util.List;
import lombok.AllArgsConstructor; // Importa la anotación AllArgsConstructor de Lombok para generar un constructor con todos los argumentos
import lombok.Data; // Importa la anotación Data de Lombok para generar getters, setters, toString, equals y hashCode
import lombok.EqualsAndHashCode; // Importa la anotación EqualsAndHashCode de Lombok para generar métodos equals y hashCode
import lombok.Getter;
import lombok.NoArgsConstructor; // Importa la anotación NoArgsConstructor de Lombok para generar un constructor sin argumentos
import lombok.Setter;
import lombok.experimental.SuperBuilder; // Importa la anotación SuperBuilder de Lombok para generar un builder que soporta herencia
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*; 


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "docente")
@EqualsAndHashCode(callSuper = true)
public class Docente extends Persona {

    @Column(name = "nro_empleado", nullable = false, unique = true)
    private String nroEmpleado;

    @Column(name = "departamento", nullable = false)
    private String departamento;

    /**
     * Lista de evaluaciones asociadas al docente.
     */
    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluacionDocente> evaluaciones;
}
