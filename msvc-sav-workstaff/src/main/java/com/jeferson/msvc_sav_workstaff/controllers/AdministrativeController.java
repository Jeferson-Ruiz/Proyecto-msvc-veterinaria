package com.jeferson.msvc_sav_workstaff.controllers;

import com.jeferson.msvc_sav_workstaff.dto.AdministrativeDto;
import com.jeferson.msvc_sav_workstaff.mapper.AdministrativeMapper;
import com.jeferson.msvc_sav_workstaff.models.Administrative;
import com.jeferson.msvc_sav_workstaff.services.AdministrativeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PatchMapping("/update/area/{idEmployee}")
    public ResponseEntity<?> uptInfoAdministrativeWorkArea(@PathVariable Long idEmployee,
            @RequestBody String workArea) {
        administrativeService.uptAdministrativeWorkArea(idEmployee, workArea);
        return ResponseEntity.noContent().build();
    }
}
