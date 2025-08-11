package com.jr.sav_mvsc_medicalcontrol.controllers;

import com.jr.sav_mvsc_medicalcontrol.dto.ConsultationDateUpdate;
import com.jr.sav_mvsc_medicalcontrol.dto.ConsultationDto;
import com.jr.sav_mvsc_medicalcontrol.dto.ConsultationReponseDto;
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
    public ResponseEntity<?> saveInfoConsultation(@RequestBody ConsultationDto consultationDto) {
        ConsultationReponseDto savedConsultation = consultationService.saveConsultation(consultationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedConsultation);
    }

    // Deve retorna la ultima
    @GetMapping("/idpet/{idPet}")
    public ResponseEntity<?> getLastConsultationByIdPet(@PathVariable Long idPet){
        ConsultationReponseDto consultation = consultationService.findConsultationByIdPet(idPet);
        return ResponseEntity.ok(consultation);
    }

    @GetMapping("/all/pet/{idPet}")
    public ResponseEntity<?> getAllConsultationsByIdPet(@PathVariable Long idPet){
        List<ConsultationReponseDto> consultations = consultationService.findAllConsultationById(idPet);
        return ResponseEntity.ok(consultations);
    }

    @PatchMapping("update/date/{idConsultation}")
    public ResponseEntity<?> updateConsultationDate(@PathVariable Long idConsultation, @RequestBody ConsultationDateUpdate newDate){
        consultationService.updateConsultationDate(idConsultation, newDate.getNewDate());
        return ResponseEntity.noContent().build();
    }
}