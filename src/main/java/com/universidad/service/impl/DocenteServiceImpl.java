package com.universidad.service.impl;

import com.universidad.dto.DocenteDTO;
import com.universidad.model.Docente;
import com.universidad.repository.DocenteRepository;
import com.universidad.service.IDocenteService;
import com.universidad.validation.DocenteValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocenteServiceImpl implements IDocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private DocenteValidator docenteValidator;

    public DocenteServiceImpl(DocenteRepository docenteRepository, DocenteValidator docenteValidator) {
        this.docenteRepository = docenteRepository;
        this.docenteValidator = docenteValidator;
    }

    @Override
    @Cacheable(value = "docentes")
    public List<DocenteDTO> obtenerTodosLosDocentes() {
        return docenteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "docente", key = "#nroEmpleado")
    public DocenteDTO obtenerDocentePorNroEmpleado(String nroEmpleado) {
        Docente docente = docenteRepository.findByNroEmpleado(nroEmpleado)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado con número de empleado: " + nroEmpleado));
        return convertToDTO(docente);
    }


    @Override
    @CachePut(value = "docente", key = "#result.nroEmpleado")
    @CacheEvict(value = "docentes", allEntries = true)
    public DocenteDTO crearDocente(DocenteDTO docenteDTO) {
        docenteValidator.validacionCompletaDocente(docenteDTO);
        Docente docente = convertToEntity(docenteDTO);
        Docente guardado = docenteRepository.save(docente);
        return convertToDTO(guardado);
    }

    @Override
    @CachePut(value = "docente", key = "#id")
    @CacheEvict(value = "docentes", allEntries = true)
    public DocenteDTO actualizarDocente(Long id, DocenteDTO docenteDTO) {
        Docente docenteExistente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        docenteExistente.setNombre(docenteDTO.getNombre());
        docenteExistente.setApellido(docenteDTO.getApellido());
        docenteExistente.setEmail(docenteDTO.getEmail());
        docenteExistente.setFechaNacimiento(docenteDTO.getFechaNacimiento());
        docenteExistente.setNroEmpleado(docenteDTO.getNroEmpleado());
        docenteExistente.setDepartamento(docenteDTO.getDepartamento());

        Docente docenteActualizado = docenteRepository.save(docenteExistente);
        return convertToDTO(docenteActualizado);
    }


    @Override
    @CacheEvict(value = {"docente", "docentes"}, allEntries = true)
    public void eliminarDocente(Long id) {
        Docente docenteExistente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        docenteRepository.delete(docenteExistente);
    }




    // Conversión de entidad a DTO
    private DocenteDTO convertToDTO(Docente docente) {
        return DocenteDTO.builder()
                .id(docente.getId())
                .nombre(docente.getNombre())
                .apellido(docente.getApellido())
                .email(docente.getEmail())
                .fechaNacimiento(docente.getFechaNacimiento())
                .nroEmpleado(docente.getNroEmpleado())
                .departamento(docente.getDepartamento())
                .build();
    }

    // Conversión de DTO a entidad
    private Docente convertToEntity(DocenteDTO dto) {
        return Docente.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .fechaNacimiento(dto.getFechaNacimiento())
                .nroEmpleado(dto.getNroEmpleado())
                .departamento(dto.getDepartamento())
                .build();
    }
}
