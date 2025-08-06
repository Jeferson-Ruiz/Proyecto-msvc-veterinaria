package com.jr.sav_mvsc_medicalcontrol.controllers;

import java.util.List;
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
        VaccineDto vaccine = vaccineService.saveVaccine(vaccineDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(vaccine);
    }

    @GetMapping("/id/{idVaccine}")
    public ResponseEntity<?> getVaccineById(@PathVariable Long idVaccine){
        VaccineDto vaccine = vaccineService.findVaccineById(idVaccine);
        return ResponseEntity.ok(vaccine);
        
    } 

    @GetMapping("/vacciness/idpet/{idPet}")
    public ResponseEntity<?> getVaccinesIdPet(@PathVariable Long idPet){
        List<VaccineDto> vaccine = vaccineService.findVaccinesIdPet(idPet);
        return ResponseEntity.ok(vaccine);
    }

    @DeleteMapping("id/{idVaccine}")
    public ResponseEntity<?> deleteInfoVaccine(@PathVariable Long idVaccine){
        vaccineService.deleteVaccine(idVaccine);
        return ResponseEntity.noContent().build();
    }

}