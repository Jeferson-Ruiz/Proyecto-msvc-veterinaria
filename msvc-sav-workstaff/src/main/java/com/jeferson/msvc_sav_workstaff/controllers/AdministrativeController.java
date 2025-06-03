package com.jeferson.msvc_sav_workstaff.controllers;

import com.jeferson.msvc_sav_workstaff.dto.AdministrativeDto;
import com.jeferson.msvc_sav_workstaff.mapper.AdministrativeMapper;
import com.jeferson.msvc_sav_workstaff.models.Administrative;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.services.AdministrativeService;
import jakarta.persistence.EntityNotFoundException;
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
import java.util.Optional;

@RestController
@RequestMapping("api/sav/employee/administrative")
public class AdministrativeController {

    private final AdministrativeService administrativeService;
    private final AdministrativeMapper administrativeMapper;

    public AdministrativeController(AdministrativeService administrativeService,
            AdministrativeMapper administrativeMapper) {
        this.administrativeService = administrativeService;
        this.administrativeMapper = administrativeMapper;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoAdministrative(@RequestBody AdministrativeDto administrative) {
        Optional<Administrative> optAdministrative = administrativeService.saveAdministrative(administrative);

        if (optAdministrative.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error usuario ya registrado en el sistema");
        }
        Administrative adminRegist = optAdministrative.get();
        AdministrativeDto adminRespo = administrativeMapper.toDto(adminRegist);
        return ResponseEntity.status(HttpStatus.CREATED).body(adminRespo);
    }

    @GetMapping("/{idEmployee}")
    public ResponseEntity<?> getAdministrative(@PathVariable Long idEmployee) {
        if (administrativeService.findByAdministrative(idEmployee).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(administrativeService.findByAdministrative(idEmployee));
    }

    @PatchMapping("/update/area/{idEmployee}")
    public ResponseEntity<?> uptInfoAdministrativeWorkArea(@PathVariable Long idEmployee, @RequestBody String workArea) {
        try {
            administrativeService.uptAdministrativeWorkArea(idEmployee, workArea);
            return ResponseEntity.noContent().build();   
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }    
    }

    @PatchMapping("/update/email/{idEmployee}")
    public ResponseEntity<?> updInfoEmail(@PathVariable Long idEmployee, @RequestBody String email) {
        try {
            administrativeService.updateEmail(idEmployee, email);
            return ResponseEntity.noContent().build();

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/update/number/{idEmployee}")
    public ResponseEntity<?> updInfoNumberPhone(@PathVariable Long idEmployee, @RequestBody Long PhoneNumber) {
        try {
            administrativeService.updateNumberPhone(idEmployee, PhoneNumber);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/update/contract/{idEmployee}")
    public ResponseEntity<?> updInfoContractType(@PathVariable Long idEmployee,
            @RequestBody ContractType contractType) {
        try {
            administrativeService.updateContractType(idEmployee, contractType);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idEmployee}")
    public ResponseEntity<?> deleteAdministrative(@PathVariable Long idEmployee) {
        try {
            administrativeService.delete(idEmployee);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
