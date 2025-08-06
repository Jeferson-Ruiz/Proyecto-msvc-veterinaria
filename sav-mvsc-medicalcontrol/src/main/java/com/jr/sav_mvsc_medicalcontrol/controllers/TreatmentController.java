package com.jr.sav_mvsc_medicalcontrol.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jr.sav_mvsc_medicalcontrol.dto.TreatmentDto;
import com.jr.sav_mvsc_medicalcontrol.services.TreatmentService;

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
        TreatmentDto treatmentDto = treatmentService.findTreatmentById(idTreatment);
        return ResponseEntity.ok(treatmentDto);   
    }

    @PostMapping
    public ResponseEntity<?> saveInfoTreatment(@RequestBody TreatmentDto treatmentdDto){
        TreatmentDto treatmentDto = treatmentService.saveTreatment(treatmentdDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(treatmentDto);
    }


    // @DeleteMapping("idtreatment/{idTreatment}")
    // public ResponseEntity<?> deleteInfoTreatment(@PathVariable Long idTreatment){
    //     treatmentService.
    // }
}