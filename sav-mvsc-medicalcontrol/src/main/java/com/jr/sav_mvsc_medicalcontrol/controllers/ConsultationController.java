package com.jr.sav_mvsc_medicalcontrol.controllers;

import com.jr.sav_mvsc_medicalcontrol.dto.ConsultationDto;
import com.jr.sav_mvsc_medicalcontrol.dto.PetDto;
import com.jr.sav_mvsc_medicalcontrol.models.Consultation;
import com.jr.sav_mvsc_medicalcontrol.services.ConsultationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("id/{idConsultation}")
    public ResponseEntity<?> getConsultationBYId(@PathVariable Long idConsultation) {
        Optional<Consultation> optConsultatio = consultationService.finConsultionById(idConsultation);
        if (optConsultatio.isPresent()) {
            return ResponseEntity.ok(optConsultatio.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("La colsulta con el ID: " + idConsultation + " no se encuentra en el sistema");
    }

    @PostMapping
    public ResponseEntity<?> saveInfoConsultation(@RequestBody Consultation consultation) {
        Optional<PetDto> petOptional = consultationService.findConsultationByIdPet(consultation.getIdPet());

        if (petOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La mascota: " + consultation.getIdPet() + " no se encuentra registrada en el sistema");
        }
        return new ResponseEntity<>(consultationService.saveConsultation(consultation), HttpStatus.CREATED);
    }

    @DeleteMapping("id/{idConsultation}")
    public ResponseEntity<?> deleteInfoConsultation(@PathVariable Long idConsultation) {
        if (consultationService.finConsultionById(idConsultation).isPresent()) {
            consultationService.deleteConsultation(idConsultation);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("La consulta con el id: " + idConsultation + " no existe en el sistema");
    }
}