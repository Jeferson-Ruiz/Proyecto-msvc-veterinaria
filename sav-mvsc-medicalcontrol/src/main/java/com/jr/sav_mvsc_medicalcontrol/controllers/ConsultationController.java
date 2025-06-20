package com.jr.sav_mvsc_medicalcontrol.controllers;

import com.jr.sav_mvsc_medicalcontrol.dto.ConsultationDto;
import com.jr.sav_mvsc_medicalcontrol.services.ConsultationService;
import jakarta.persistence.EntityNotFoundException;
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

    @GetMapping("/id/{idConsultation}")
    public ResponseEntity<?> getConsultationBYId(@PathVariable Long idConsultation) {
        Optional<ConsultationDto> optConsultatio = consultationService.finConsultionById(idConsultation);
        if (optConsultatio.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No existe resgistro de consulta con el ID: "+ idConsultation+ " en el sistema");
        }
        return ResponseEntity.ok(optConsultatio.get());
    }

    @GetMapping("/idpet/{idPet}")
    public ResponseEntity<?> getConsultationByIdPet(@PathVariable Long idPet){
        Optional<ConsultationDto> optConsultation = consultationService.findConsultationByIdPet(idPet);
        if(optConsultation.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No existe consulta asociada con el paciente: "+ idPet+ " en el sistema");    
        }
        return ResponseEntity.ok(optConsultation.get());
    }

    @PostMapping
    public ResponseEntity<?> saveInfoConsultation(@RequestBody ConsultationDto consultationDto) {
        Optional<ConsultationDto> consultationOpt = consultationService.saveConsultation(consultationDto);
        if (consultationOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error, no se pueden registrar consultas porque el paciente "
                            + consultationDto.getIdPet() + " no existe en el sistema");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(consultationOpt);
    }

    @DeleteMapping("id/{idConsultation}")
    public ResponseEntity<?> deleteInfoConsultation(@PathVariable Long idConsultation) {
        try {
            consultationService.deleteConsultation(idConsultation);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error, no existe resgistro de consulta con el ID: "+ idConsultation+ " en el sistema");        }
    }
}