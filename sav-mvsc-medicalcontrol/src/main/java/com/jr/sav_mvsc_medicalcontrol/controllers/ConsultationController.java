package com.jr.sav_mvsc_medicalcontrol.controllers;

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
    public ResponseEntity <List<Consultation>> getAllConsultation(){
        return  ResponseEntity.ok(consultationService.findAllConsultations());
    }

    @GetMapping("id/{idConsultation}")
    public ResponseEntity<?> getConsultationBYId(@PathVariable Long idConsultation){
        Optional<Consultation> optConsultatio = consultationService.finConsultionById(idConsultation);
        if(optConsultatio.isPresent()){
            return  ResponseEntity.ok(optConsultatio.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("La colsulta con el ID: "+idConsultation+" no se encuentra en el sistema");
    }

    @GetMapping("idpet/{idPet}")
    public ResponseEntity<?> getConsultionByIdPet(@PathVariable Long idPet){
        Optional<Consultation> optConsultation = consultationService.finConsultionByIdPet(idPet);
        if(optConsultation.isPresent()){
            return ResponseEntity.ok(optConsultation.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra consulta para la mascota:" + idPet);
    }

    @PostMapping
    public ResponseEntity<Consultation> saveInfoConsultation(@RequestBody Consultation consultation){
        return new ResponseEntity<>(consultationService.saveConsultation(consultation), HttpStatus.CREATED);
    }

    @DeleteMapping("id/{idConsultation}")
    public ResponseEntity<?> deleteInfoConsultation(@PathVariable Long idConsultation){
        if(consultationService.finConsultionById(idConsultation).isPresent()){
            consultationService.deleteConsultation(idConsultation);
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("El id: "+idConsultation +" no existe en el sistema");
    }
}