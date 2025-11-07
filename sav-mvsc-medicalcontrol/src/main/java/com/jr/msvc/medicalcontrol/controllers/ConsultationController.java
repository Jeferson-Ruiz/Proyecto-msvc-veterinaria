package com.jr.msvc.medicalcontrol.controllers;

import com.jr.msvc.medicalcontrol.services.ConsultationService;
import com.jr.msvc.medicalcontrol.dto.consultatio.ConsultationDate;
import com.jr.msvc.medicalcontrol.dto.consultatio.ConsultationDateUpdate;
import com.jr.msvc.medicalcontrol.dto.consultatio.ConsultationRequestDto;
import com.jr.msvc.medicalcontrol.dto.vet.VetWithConsultationsDto;
import com.jr.msvc.medicalcontrol.models.AttendanceStatus;
import com.jr.msvc.medicalcontrol.dto.consultatio.ConsultationReponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/consultation")
public class ConsultationController {

    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoConsultation(@Valid @RequestBody ConsultationRequestDto consultationDto) {
        ConsultationReponseDto savedConsultation = consultationService.saveConsultation(consultationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedConsultation);
    }

    @GetMapping
    public ResponseEntity<List<ConsultationReponseDto>> getAllConsultation() {
        return ResponseEntity.ok(consultationService.findAllConsultations());
    }

    @GetMapping("/code/{consultationCode}")
    public ResponseEntity<?> getConsultationByCode(@PathVariable String consultationCode) {
        ConsultationReponseDto consultation = consultationService.findConsultionByCode(consultationCode);
        return ResponseEntity.ok(consultation);
    }
    

    @GetMapping("/all/pet/{petCode}")
    public ResponseEntity<?> getAllConsultationsByIdPet(@PathVariable String petCode){
        List<ConsultationReponseDto> consultations = consultationService.findAllConsultationByPetCode(petCode);
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

    @GetMapping("/vet/{vetCode}")
    public ResponseEntity<VetWithConsultationsDto> getVetWithConsultations(@PathVariable String vetCode) {
        return ResponseEntity.ok(consultationService.findConsultationsByVetCode(vetCode));
    }

    @PatchMapping("update-date/{consultationCode}")
    public ResponseEntity<?> updateConsultationDate(@PathVariable String consultationCode, @Valid @RequestBody ConsultationDateUpdate newDate){
        consultationService.updateConsultationDate(consultationCode, newDate.getNewDate());
        return ResponseEntity.noContent().build();
    }
}