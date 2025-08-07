package com.jr.sav_mvsc_medicalcontrol.controllers;

import com.jr.sav_mvsc_medicalcontrol.dto.ConsultationDto;
import com.jr.sav_mvsc_medicalcontrol.services.ConsultationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/sav/consultation")
public class ConsultationController {

    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping
    public ResponseEntity<List<ConsultationDto>> getAllConsultation() {
        return ResponseEntity.ok(consultationService.findAllConsultations());
    }

    @GetMapping("/id/{idConsultation}")
    public ResponseEntity<?> getConsultationById(@PathVariable Long idConsultation) {
        ConsultationDto consultation = consultationService.findConsultionById(idConsultation);
        return ResponseEntity.ok(consultation);
    }

    @GetMapping("/idpet/{idPet}")
    public ResponseEntity<?> getConsultationByIdPet(@PathVariable Long idPet){
        ConsultationDto consultation = consultationService.findConsultationByIdPet(idPet);
        return ResponseEntity.ok(consultation);
    }

    @GetMapping("/all/pet/{idPet}")
    public ResponseEntity<?> getAllConsultationsByIdPet(@PathVariable Long idPet){
        List<ConsultationDto> consultations = consultationService.findAllConsultationById(idPet);
        return ResponseEntity.ok(consultations);
    }

    @PostMapping
    public ResponseEntity<?> saveInfoConsultation(@RequestBody ConsultationDto consultationDto) {
        ConsultationDto savedConsultation = consultationService.saveConsultation(consultationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedConsultation);
    }

    @DeleteMapping("id/{idConsultation}")
    public ResponseEntity<?> deleteInfoConsultation(@PathVariable Long idConsultation) {
        consultationService.deleteConsultation(idConsultation);
        return ResponseEntity.noContent().build();
    }
}