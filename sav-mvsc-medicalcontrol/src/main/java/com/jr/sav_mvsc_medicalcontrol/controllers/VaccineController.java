package com.jr.sav_mvsc_medicalcontrol.controllers;

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
import com.jr.sav_mvsc_medicalcontrol.dto.VaccineDateUpdate;
import com.jr.sav_mvsc_medicalcontrol.dto.VaccineRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.VaccineResponseDto;
import com.jr.sav_mvsc_medicalcontrol.services.VaccineService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/sav/vaccine")
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

    @GetMapping("all/idpet/{idPet}")
    public ResponseEntity<?> getAllVaccinesIdPet(@PathVariable Long idPet) {
        List<VaccineResponseDto> vaccine = vaccineService.findVaccinesIdPet(idPet);
        return ResponseEntity.ok(vaccine);
    }

    @PatchMapping("update/name/id/{idVaccine}")
    public ResponseEntity<?> updateVaccineName(@PathVariable Long idVaccine, @RequestBody String newName) {
        vaccineService.updateName(idVaccine, newName);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("update/date/id/{idVaccine}")
    public ResponseEntity<?> updateNexAppication(@PathVariable Long idVaccine, @Valid @RequestBody VaccineDateUpdate newDate) {
        vaccineService.updateNextApplicationDate(idVaccine, newDate.getNewDate());
        return ResponseEntity.noContent().build();
    }

}