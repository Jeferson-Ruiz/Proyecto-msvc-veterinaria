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
import com.jr.sav_mvsc_medicalcontrol.dto.TreatmentDto;
import com.jr.sav_mvsc_medicalcontrol.services.TreatmentService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api/sav/treatment")
public class TreatmentController {

    private final TreatmentService treatmentService;
            
    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping
    public ResponseEntity <List<TreatmentDto>> getAllTreatment(){
        return ResponseEntity.ok(treatmentService.findAlltreatments());
    }

    @GetMapping("idtreatment/{idTreatment}")
    public ResponseEntity<?> getTreatmentById(@PathVariable Long idTreatment){
        Optional<TreatmentDto> optTreatment = treatmentService.findTreatmentById(idTreatment);
        if (optTreatment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("El tratamiento: "+idTreatment+" no existe en el sistema");
        }
        return ResponseEntity.ok(optTreatment.get());
    }

    @PostMapping
    public ResponseEntity<?> saveInfoTreatment(@RequestBody TreatmentDto treatmentdDto){
        Optional<TreatmentDto> optTreatment = treatmentService.saveTreatment(treatmentdDto);
        if (optTreatment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Error, no se pueden registrar tratamiento porque el paciente no cuenta con cnsulta");
        }
        return ResponseEntity.ok(optTreatment);
    }


    @DeleteMapping("idtreatment/{idTreatment}")
    public ResponseEntity<?> deleteInfoTreatment(@PathVariable Long idTreatment){
        try {
            treatmentService.deleteTreatment(idTreatment);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Error, no se pueden eliminar tratamiento "+ idTreatment +" no existe en el sistema");
        }
    }
}