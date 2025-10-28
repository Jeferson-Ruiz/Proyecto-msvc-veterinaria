package com.jr.msvc.medicalcontrol.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jr.msvc.medicalcontrol.dto.treatment.TreatmentRequestDto;
import com.jr.msvc.medicalcontrol.dto.treatment.TreatmentResponseDto;
import com.jr.msvc.medicalcontrol.services.TreatmentService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("treatment")
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
    public ResponseEntity<?> saveInfoTreatment(@Valid @RequestBody TreatmentRequestDto treatmentdDto) {
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