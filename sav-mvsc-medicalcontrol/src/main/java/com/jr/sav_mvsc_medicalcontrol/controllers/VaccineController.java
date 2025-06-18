package com.jr.sav_mvsc_medicalcontrol.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jr.sav_mvsc_medicalcontrol.dto.VaccineDto;
import com.jr.sav_mvsc_medicalcontrol.services.VaccineService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api/sav/vaccine")
public class VaccineController {

    private final VaccineService vaccineService;

    public VaccineController(VaccineService vaccineService){
        this.vaccineService = vaccineService;
    }

    @GetMapping
    public ResponseEntity<List<VaccineDto>> getAllVaccines(){
        return ResponseEntity.ok(vaccineService.findAllVaccines());
    }

    @PostMapping
    public ResponseEntity<?> saveInfoVaccine(@RequestBody VaccineDto vaccineDto){
        Optional<VaccineDto> optVaccine = vaccineService.saveVaccine(vaccineDto);
        if (optVaccine.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Error, no se pueden registrar vacuna. La mascota "+ vaccineDto.getIdPet() +" no existe en el sistema");
        }
        return ResponseEntity.ok(optVaccine);
    }

    @GetMapping("id/{idVaccine}")
    public ResponseEntity<?> getVaccineById(@PathVariable Long idVaccine){
        return ResponseEntity.of(vaccineService.findVaccineById(idVaccine));
    }

    @DeleteMapping("id/{idVaccine}")
    public ResponseEntity<?> deleteInfoVaccine(@PathVariable Long idVaccine){
        try {
            vaccineService.deleteVaccine(idVaccine);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error, no existe resgistro de vacuna con el ID: "+ idVaccine+" en el sistema");       
        }
    }

}