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
import com.jr.sav_mvsc_medicalcontrol.models.Vaccine;
import com.jr.sav_mvsc_medicalcontrol.services.VaccineService;

@RestController
@RequestMapping("api/sav/vaccine")
public class VaccineController {

    private final VaccineService vaccineService;

    public VaccineController(VaccineService vaccineService){
        this.vaccineService = vaccineService;
    }

    @GetMapping
    public ResponseEntity<List<Vaccine>> getAllVaccines(){
        return ResponseEntity.ok(vaccineService.findAllVaccines());
    }

    @PostMapping
    public ResponseEntity<?> saveInfoVaccine(@RequestBody Vaccine vaccine){
        return new ResponseEntity<>(vaccineService.saveVaccine(vaccine), HttpStatus.CREATED);
    }

    @GetMapping("id/{idVaccine}")
    public ResponseEntity<?> getVaccineById(@PathVariable Long idVaccine){
        return ResponseEntity.of(vaccineService.findVaccineById(idVaccine));
    }

    @DeleteMapping("id/{idVaccine}")
    public ResponseEntity<?> deleteInfoVaccine(@PathVariable Long idVaccine){
        Optional<Vaccine> optVaccine = vaccineService.findVaccineById(idVaccine);

        if (optVaccine.isPresent()) {
            vaccineService.deleteVaccine(idVaccine);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("La vacuna: "+idVaccine +" no existe en el sistema");
    }
}