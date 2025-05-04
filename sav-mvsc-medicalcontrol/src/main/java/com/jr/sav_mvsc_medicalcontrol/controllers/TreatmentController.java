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
import com.jr.sav_mvsc_medicalcontrol.models.Treatment;
import com.jr.sav_mvsc_medicalcontrol.services.TreatmentService;

@RestController
@RequestMapping("api/sav/treatment")
public class TreatmentController {

    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping
    public ResponseEntity<List<Treatment>> getAllTreatment(){
        return ResponseEntity.ok(treatmentService.findAlltreatments());
    }

    @GetMapping("idtreatment/{idTreatment}")
    public ResponseEntity<?> getTreatmentById(@PathVariable Long idTreatment){
        Optional<Treatment> optTreatment = treatmentService.findTreatmentById(idTreatment);

        if (optTreatment. isPresent()) {
            return ResponseEntity.ok(optTreatment.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("El tratamiento: "+idTreatment+" no existe en el sistema");
    }

    @PostMapping
    public ResponseEntity<?> saveInfoTreatment(@RequestBody Treatment treatment){
        return new ResponseEntity<>(treatmentService.saveTreatment(treatment),HttpStatus.CREATED);
    }


    @DeleteMapping("idtreatment/{idTreatment}")
    public ResponseEntity<?> deleteInfoTreatment(@PathVariable Long idTreatment){
        Optional<Treatment> optTreatment = treatmentService.findTreatmentById(idTreatment);

        if (optTreatment.isPresent()) {
            treatmentService.deleteTreatment(idTreatment);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("El tratamiento: " + idTreatment + " no existe en el sistema");
    }
}