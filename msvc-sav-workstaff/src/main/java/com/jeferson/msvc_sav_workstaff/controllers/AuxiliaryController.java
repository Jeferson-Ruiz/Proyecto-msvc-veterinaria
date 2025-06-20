package com.jeferson.msvc_sav_workstaff.controllers;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryDto;
import com.jeferson.msvc_sav_workstaff.mapper.AuxiliaryMapper;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.services.AuxiliaryService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api/sav/employee/auxiliary")
public class AuxiliaryController {

    private final AuxiliaryService auxService;

    public AuxiliaryController(AuxiliaryService auxService, AuxiliaryMapper auxMapper) {
        this.auxService = auxService;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoAuxiliary(@RequestBody AuxiliaryDto auxiliaryDto) {
        Optional<AuxiliaryDto> optAuxiliary = auxService.saveAuxiliary(auxiliaryDto);

        if (optAuxiliary.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error de registr\nusuario ya existe en el sistema");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(optAuxiliary.get());

    }

    @GetMapping("/{idEmployee}")
    public ResponseEntity<?> getAuxiliary(@PathVariable Long idEmployee) {
        if (auxService.findById(idEmployee).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe auxiliar con el id: " + idEmployee + " en el sistema");
        }
        return ResponseEntity.ok(auxService.findById(idEmployee));
    }

    @PatchMapping("/update/email/{idEmployee}")
    public ResponseEntity<?> uptInfoEmail(@PathVariable Long idEmployee, @RequestBody String email) {
        try {
            auxService.updateEmail(idEmployee, email);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error de actualizacion\nNo existe auxiliar con el id: " + idEmployee);
        }
    }

    @PatchMapping("/update/number/{idEmployee}")
    public ResponseEntity<?> uptInfoPhoneNumber(@PathVariable Long idEmployee, @RequestBody Long phoneNumber) {
        try {
            auxService.updatePhoneNumber(idEmployee, phoneNumber);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error de actualizacion\nNo existe auxiliar con el id: " + idEmployee);
        }
    }

    @PatchMapping("/update/contract/{idEmployee}")
    public ResponseEntity<?> updInfoContractType(@PathVariable Long idEmployee,
            @RequestBody ContractType contractType) {
        try {
            auxService.updateContractType(idEmployee, contractType);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error de actualizacion\nNo existe auxiliar con el id: " + idEmployee);
        }
    }

    @PatchMapping("/update/status/{idEmployee}")
    public ResponseEntity<?> updateInfoWorkStatus(@PathVariable Long idEmployee, @RequestBody Boolean workStatus) {
        try {
            auxService.updateWorkStatus(idEmployee, workStatus);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error de actualizacion\nNo existe auxiliar con el id: " + idEmployee);
        }
    }

    @DeleteMapping("/{idEmployee}")
    public ResponseEntity<?> deleteAuxiliary(@PathVariable Long idEmployee) {
        try {
            auxService.delete(idEmployee);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error de actualizacion\nNo existe administrativo con el id: " + idEmployee);
        }
    }
}
