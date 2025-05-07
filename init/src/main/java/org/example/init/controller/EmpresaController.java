package org.example.init.controller;


import org.example.init.DTO.EmpresaDTO;
import org.example.init.DTO.EmpresaStatusDTO;
import org.example.init.domain.Empresa;
import org.example.init.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/companies")
public class EmpresaController {
    @Autowired
    private EmpresaRepository empresaRepository;

    public EmpresaController(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @PostMapping
    public ResponseEntity<?> guardarEmpresa(@RequestBody EmpresaDTO dto) {
        try {
            Empresa empresa = new Empresa();
            empresa.setAdmin(dto.getAdmin());
            empresa.setRuc(dto.getRuc());
            empresa.setFechaAfiliacion(dto.getFechaAfiliacion());
            empresa.setStatus(dto.isStatus());
            empresa.setConsumo(dto.getConsumo());

            Empresa savedEmpresa = empresaRepository.save(empresa);
            return ResponseEntity.ok(savedEmpresa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hubo un error al guardar la empresa");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hubo un error :(");
        }
    }


    @GetMapping
    ResponseEntity<?> listarEmpresa() {
        List<Empresa> empresas = empresaRepository.findAll();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> mostrarEmpresa(@PathVariable Long id) {
        Optional<Empresa> persona = empresaRepository.findById(id);
        return ResponseEntity.ok(persona);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEmpresa(@PathVariable Long id, @RequestBody EmpresaDTO dto) {
        try {
            Optional<Empresa> empresaOpt = empresaRepository.findById(id);
            if (empresaOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa no encontrada con ID: " + id);
            }

            Empresa empresa = empresaOpt.get();
            empresa.setAdmin(dto.getAdmin());
            empresa.setRuc(dto.getRuc());
            empresa.setFechaAfiliacion(dto.getFechaAfiliacion());
            empresa.setStatus(dto.isStatus());
            empresa.setConsumo(dto.getConsumo());

            Empresa updatedEmpresa = empresaRepository.save(empresa);
            return ResponseEntity.ok(updatedEmpresa);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos inválidos: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Violación de integridad: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<?> actualizarEstadoEmpresa(@PathVariable Long id, @RequestBody EmpresaStatusDTO estadoDTO) {
        try {
            Optional<Empresa> empresaOpt = empresaRepository.findById(id);

            if (empresaOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa no encontrada con ID: " + id);
            }

            Empresa empresa = empresaOpt.get();
            empresa.setStatus(estadoDTO.getStatus());

            empresaRepository.save(empresa);

            return ResponseEntity.ok("Estado de la empresa actualizado a: " + (estadoDTO.getStatus() ? "ACTIVO" : "INACTIVO"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el estado: " + e.getMessage());
        }
    }

}








