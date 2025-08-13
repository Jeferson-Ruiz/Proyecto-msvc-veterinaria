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
import com.jr.sav_mvsc_medicalcontrol.dto.treatment.TreatmentDto;
import com.jr.sav_mvsc_medicalcontrol.dto.treatment.TreatmentResponseDto;
import com.jr.sav_mvsc_medicalcontrol.services.TreatmentService;

@RestController
@RequestMapping("api/sav/treatment")
public class TreatmentController {

    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping
    public ResponseEntity<List<TreatmentResponseDto>> getAllTreatment() {
        return ResponseEntity.ok(treatmentService.findAlltreatments());
    }

    @GetMapping("id/{idTreatment}")
    public ResponseEntity<?> getTreatmentById(@PathVariable Long idTreatment) {
        TreatmentResponseDto treatmentDto = treatmentService.findTreatmentById(idTreatment);
        return ResponseEntity.ok(treatmentDto);
    }

    @PostMapping
    public ResponseEntity<?> saveInfoTreatment(@RequestBody TreatmentDto treatmentdDto) {
        TreatmentResponseDto treatmentDto = treatmentService.saveTreatment(treatmentdDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(treatmentDto);
    }

    @GetMapping("/all/pet/{idPet}")
    public ResponseEntity<?> getAllTreatmentByIdPet(@PathVariable Long idPet) {
        List<TreatmentResponseDto> treatmentDto = treatmentService.findTreatmentByIdPet(idPet);
        return ResponseEntity.ok(treatmentDto);
    }

    @GetMapping("/all/consultation/{idConsu}")
    public ResponseEntity<?> getAllTreatmentByIdConsultation(@PathVariable Long idConsu) {
        List<TreatmentResponseDto> treatments = treatmentService.findTreatmentByIdConsultation(idConsu);
        return ResponseEntity.ok(treatments);
    }

}