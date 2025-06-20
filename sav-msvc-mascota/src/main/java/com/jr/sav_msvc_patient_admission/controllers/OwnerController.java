package com.jr.sav_msvc_patient_admission.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jr.sav_msvc_patient_admission.dto.OwnerDto;
import com.jr.sav_msvc_patient_admission.services.OwnerService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api/sav/owner")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAllOwners() {
        return ResponseEntity.ok(ownerService.findAllOwners());
    }

    @GetMapping("/id/{idOwner}")
    public ResponseEntity<?> getOwnerById(@PathVariable Long idOwner) {
        Optional<OwnerDto> optOwner = ownerService.findOwnerById(idOwner);
        if (optOwner.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El propietario con el documento " + idOwner + " no existe en el sistema");
        }
        return ResponseEntity.ok(optOwner.get());
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<?> getOwnerByDocumentNumber(@PathVariable Long documentNumber) {
        Optional<OwnerDto> optOwner = ownerService.findOwnerByDocumentNumber(documentNumber);
        if (optOwner.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El propietario con el documento " + documentNumber + " no existe en el sistema");
        }
        return ResponseEntity.ok(optOwner.get());
    }

    @PostMapping
    public ResponseEntity<?> saveInfoOwner(@RequestBody OwnerDto owner) {
        Optional<OwnerDto> optOwner = ownerService.saveOwner(owner);
        if (optOwner.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Propietario con la identificacion: " + owner.getDocumentNumber()
                            + " ya existe en el sistema");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(optOwner.get());
    }

    @DeleteMapping("/id/{idOwner}")
    public ResponseEntity<?> deleteInfoOwner(@PathVariable Long idOwner) {
        try {
            ownerService.deleteOwnerById(idOwner);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Propietario con el ID : " + idOwner + " no existe en el sistema");
        }
    }

    @PatchMapping("/updatePhone/{idOwner}")
    public ResponseEntity<?> updateIntoPhoneNumber(@PathVariable Long idOwner, @RequestBody Long phoneNumber) {
        try {
            ownerService.updatePhoneNumber(idOwner, phoneNumber);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Propietario con el ID : " + idOwner + " no se encuentra registrado en el sistema");
        }
    }

    @PatchMapping("/updateEmail/{idOwner}")
    public ResponseEntity<?> updateInfoEmail(@PathVariable Long idOwner, @RequestBody String email) {
        try {
            ownerService.updateEmail(idOwner, email);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Propietario con el ID : " + idOwner + " no se encuentra registrado en el sistema");
        }
    }
}