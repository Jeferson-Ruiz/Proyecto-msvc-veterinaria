package com.jr.sav_msvc_patient_admission.controllers;

import java.util.List;
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
        OwnerDto owner = ownerService.findOwnerById(idOwner).orElseThrow();
        return ResponseEntity.ok(owner);
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<?> getOwnerByDocumentNumber(@PathVariable Long documentNumber) {
        return ownerService.findOwnerByDocumentNumber(documentNumber)
            .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> saveInfoOwner(@RequestBody OwnerDto owner) {
        OwnerDto created = ownerService.saveOwner(owner)
                .orElseThrow(); 
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/id/{idOwner}")
    public ResponseEntity<?> deleteInfoOwner(@PathVariable Long idOwner) {
        ownerService.deleteOwnerById(idOwner);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/updatePhone/{idOwner}")
    public ResponseEntity<?> updateIntoPhoneNumber(@PathVariable Long idOwner, @RequestBody Long phoneNumber) {
        ownerService.updatePhoneNumber(idOwner, phoneNumber);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/updateEmail/{idOwner}")
    public ResponseEntity<?> updateInfoEmail(@PathVariable Long idOwner, @RequestBody String email) {
        ownerService.updateEmail(idOwner, email);
        return ResponseEntity.noContent().build();
    }
}