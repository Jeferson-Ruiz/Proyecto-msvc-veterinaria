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
@RequestMapping("/treatment")
public class TreatmentController {

    private final TreatmentService treatmentService;

    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoTreatment(@Valid @RequestBody TreatmentRequestDto treatmentdDto) {
        TreatmentResponseDto treatmentDto = treatmentService.saveTreatment(treatmentdDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(treatmentDto);
    }

    @GetMapping
    public ResponseEntity<List<TreatmentResponseDto>> getAllTreatment() {
        return ResponseEntity.ok(treatmentService.findAlltreatments());
    }

    @GetMapping("code/{treatmentCode}")
    public ResponseEntity<?> getTreatmentById(@PathVariable String treatmentCode) {
        TreatmentResponseDto treatmentDto = treatmentService.findTreatmentByCode(treatmentCode);
        return ResponseEntity.ok(treatmentDto);
    }

    @GetMapping("/all/pet/{petCode}")
    public ResponseEntity<?> getAllTreatmentByIdPet(@PathVariable String petCode) {
        List<TreatmentResponseDto> treatmentDto = treatmentService.findTreatmentByPetCode(petCode);
        return ResponseEntity.ok(treatmentDto);
    }

    @GetMapping("/all/consultation/{consultationCode}")
    public ResponseEntity<?> getAllTreatmentByIdConsultation(@PathVariable String consultationCode) {
        List<TreatmentResponseDto> treatments = treatmentService.findTreatmentByConsultationCode(consultationCode);
        return ResponseEntity.ok(treatments);
    }

}