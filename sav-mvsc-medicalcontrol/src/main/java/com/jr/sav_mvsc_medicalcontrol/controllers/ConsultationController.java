package com.jr.sav_mvsc_medicalcontrol.controllers;

import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationDateUpdate;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationReponseDto;
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
    public ResponseEntity<List<ConsultationReponseDto>> getAllConsultation() {
        return ResponseEntity.ok(consultationService.findAllConsultations());
    }

    @GetMapping("/id/{idConsultation}")
    public ResponseEntity<?> getConsultationById(@PathVariable Long idConsultation) {
        ConsultationReponseDto consultation = consultationService.findConsultionById(idConsultation);
        return ResponseEntity.ok(consultation);
    }
    
    @PostMapping
    public ResponseEntity<?> saveInfoConsultation(@RequestBody ConsultationRequestDto consultationDto) {
        ConsultationReponseDto savedConsultation = consultationService.saveConsultation(consultationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedConsultation);
    }

    @GetMapping("/all/pet/{idPet}")
    public ResponseEntity<?> getAllConsultationsByIdPet(@PathVariable Long idPet){
        List<ConsultationReponseDto> consultations = consultationService.findAllConsultationByIdPet(idPet);
        return ResponseEntity.ok(consultations);
    }

    @PatchMapping("update/date/{idConsultation}")
    public ResponseEntity<?> updateConsultationDate(@PathVariable Long idConsultation, @RequestBody ConsultationDateUpdate newDate){
        consultationService.updateConsultationDate(idConsultation, newDate.getNewDate());
        return ResponseEntity.noContent().build();
    }
}