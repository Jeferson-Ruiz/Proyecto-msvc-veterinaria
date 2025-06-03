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
import com.jeferson.msvc_sav_workstaff.models.Auxiliary;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.services.AuxiliaryService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api/sav/employee/auxiliary")
public class AuxiliaryController {

    private final AuxiliaryService auxService;
    private final AuxiliaryMapper auxMapper;

    public AuxiliaryController(AuxiliaryService auxService, AuxiliaryMapper auxMapper) {
        this.auxService = auxService;
        this.auxMapper = auxMapper;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoAuxiliary(@RequestBody AuxiliaryDto auxiliaryDto) {
        Optional<Auxiliary> optAuxiliary = auxService.saveAuxiliary(auxiliaryDto);

        if (optAuxiliary.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El usuario ya existe en el sistema");
        }
        Auxiliary auxiRegist = optAuxiliary.get();
        AuxiliaryDto auxResponse = auxMapper.toDto(auxiRegist);
        return ResponseEntity.status(HttpStatus.CREATED).body(auxResponse);
    }

    @GetMapping("/{idEmployee}")
    public ResponseEntity<?> getAuxiliary(@PathVariable Long idEmployee) {
        if (auxService.findById(idEmployee).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(auxService.findById(idEmployee));
    }

    @PatchMapping("/update/email/{idEmployee}")
    public ResponseEntity<?> uptInfoEmail(@PathVariable Long idEmployee, @RequestBody String email) {
        try {
            auxService.updateEmail(idEmployee, email);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/update/number/{idEmployee}")
    public ResponseEntity<?> uptInfoPhoneNumber(@PathVariable Long idEmployee, @RequestBody Long phoneNumber) {
        try {
            auxService.updatePhoneNumber(idEmployee, phoneNumber);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/update/contract/{idEmployee}")
    public ResponseEntity<?> updInfoContractType(@PathVariable Long idEmployee,
            @RequestBody ContractType contractType) {
        try {
            auxService.updateContractType(idEmployee, contractType);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idEmployee}")
    public ResponseEntity<?> deleteAuxiliary(@PathVariable Long idEmployee) {
        try {
            auxService.delete(idEmployee);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
