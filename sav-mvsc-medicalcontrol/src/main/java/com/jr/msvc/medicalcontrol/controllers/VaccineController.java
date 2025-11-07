package com.jr.msvc.medicalcontrol.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jr.msvc.medicalcontrol.dto.vaccine.VaccineDateUpdate;
import com.jr.msvc.medicalcontrol.dto.vaccine.VaccineRequestDto;
import com.jr.msvc.medicalcontrol.dto.vaccine.VaccineResponseDto;
import com.jr.msvc.medicalcontrol.services.VaccineService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vaccine")
public class VaccineController {

    private final VaccineService vaccineService;

    public VaccineController(VaccineService vaccineService) {
        this.vaccineService = vaccineService;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoVaccine(@Valid @RequestBody VaccineRequestDto vaccineDto) {
        VaccineResponseDto vaccine = vaccineService.saveVaccine(vaccineDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(vaccine);
    }

    @GetMapping("/all/{petCode}")
    public ResponseEntity<?> getAllVaccinesIdPet(@PathVariable String petCode) {
        List<VaccineResponseDto> vaccine = vaccineService.findVaccinesPetCode(petCode);
        return ResponseEntity.ok(vaccine);
    }

    @PatchMapping("/update/name/{vaccineCode}")
    public ResponseEntity<?> updateVaccineName(@PathVariable String vaccineCode, @RequestBody String newName) {
        vaccineService.updateName(vaccineCode, newName);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/date/{vaccineCode}")
    public ResponseEntity<?> updateNexAppication(@PathVariable String vaccineCode, @Valid @RequestBody VaccineDateUpdate newDate) {
        vaccineService.updateNextApplicationDate(vaccineCode, newDate.getNewDate());
        return ResponseEntity.noContent().build();
    }

}