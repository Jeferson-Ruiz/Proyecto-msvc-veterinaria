package com.jeferson.msvc_sav_workstaff.controllers;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.msvc_sav_workstaff.dto.VetDto;
import com.jeferson.msvc_sav_workstaff.mapper.VetMapper;
import com.jeferson.msvc_sav_workstaff.models.Vet;
import com.jeferson.msvc_sav_workstaff.services.VetService;

@RestController
@RequestMapping("api/sav/employee/vet")
public class VetController {

    private final VetService vetService;
    private final VetMapper vetMapper;

    public VetController(VetService vetService, VetMapper vetMapper) {
        this.vetService = vetService;
        this.vetMapper = vetMapper;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoVet(@RequestBody VetDto vetDto) {
        Optional<Vet> optVet = vetService.saveVet(vetDto);

        if (optVet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("usuario ya existe en el sistema");
        }

        Vet vetRegist = optVet.get();
        VetDto vetResponse = vetMapper.toDto(vetRegist);
        return ResponseEntity.status(HttpStatus.CREATED).body(vetResponse);
    }

}
