package com.jr.sav_mvsc_medicalcontrol.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    

}
