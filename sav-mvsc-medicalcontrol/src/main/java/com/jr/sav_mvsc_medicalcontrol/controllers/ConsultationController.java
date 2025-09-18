package com.jr.sav_mvsc_medicalcontrol.controllers;

import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationDate;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationDateUpdate;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationRequestDto;
import com.jr.sav_mvsc_medicalcontrol.models.AttendanceStatus;
import com.jr.sav_mvsc_medicalcontrol.dto.consultatio.ConsultationReponseDto;
import com.jr.sav_mvsc_medicalcontrol.services.ConsultationService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

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
    public ResponseEntity<?> saveInfoConsultation(@Valid @RequestBody ConsultationRequestDto consultationDto) {
        ConsultationReponseDto savedConsultation = consultationService.saveConsultation(consultationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedConsultation);
    }

    @GetMapping("/all/pet/{idPet}")
    public ResponseEntity<?> getAllConsultationsByIdPet(@PathVariable Long idPet){
        List<ConsultationReponseDto> consultations = consultationService.findAllConsultationByIdPet(idPet);
        return ResponseEntity.ok(consultations);
    }

    @GetMapping("/all/bystatus/{status}")
    public ResponseEntity<?> getAllConsultationByStatus(@PathVariable AttendanceStatus status){
        List<ConsultationReponseDto> consultations = consultationService.findAllByStatus(status);
        return ResponseEntity.ok(consultations);
    }

    @GetMapping("/date")
    public ResponseEntity<?> getAllConsultationByDate(@Valid @RequestBody ConsultationDate request){
        List<ConsultationReponseDto> consultations = consultationService.findByDate(request.getDate());
        return ResponseEntity.ok(consultations);
    }


    @PatchMapping("update/date/{idConsultation}")
    public ResponseEntity<?> updateConsultationDate(@PathVariable Long idConsultation, @Valid @RequestBody ConsultationDateUpdate newDate){
        consultationService.updateConsultationDate(idConsultation, newDate.getNewDate());
        return ResponseEntity.noContent().build();
    }
}