package com.jr.sav_mvsc_medicalcontrol.controllers;

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
import com.jr.sav_mvsc_medicalcontrol.dto.OwnerDto;
import com.jr.sav_mvsc_medicalcontrol.services.OwnerService;

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

    @GetMapping("/disable")
    public ResponseEntity<List<OwnerDto>> getAllDisableOwners() {
        return ResponseEntity.ok(ownerService.findAllDisabeOwners());
    }

    @GetMapping("/active")
    public ResponseEntity<List<OwnerDto>> getAllActiveOwners() {
        return ResponseEntity.ok(ownerService.findAllActiveOwners());
    }

    @GetMapping("/id/{idOwner}")
    public ResponseEntity<?> getOwnerById(@PathVariable Long idOwner) {
        OwnerDto owner = ownerService.findOwnerById(idOwner);
        return ResponseEntity.ok(owner);
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<?> getOwnerByDocumentNumber(@PathVariable Long documentNumber) {
        OwnerDto owner = ownerService.findOwnerByDocumentNumber(documentNumber);
        return ResponseEntity.ok(owner);
    }

    @PostMapping
    public ResponseEntity<?> saveInfoOwner(@RequestBody OwnerDto owner) {
        OwnerDto ownerCreated = ownerService.saveOwner(owner);
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerCreated);
    }

    @DeleteMapping("/id/{idOwner}")
    public ResponseEntity<?> deleteInfoOwner(@PathVariable Long idOwner) {
        ownerService.disableOwnerById(idOwner);
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
