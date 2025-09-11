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
import com.jr.sav_mvsc_medicalcontrol.dto.EmailDto;
import com.jr.sav_mvsc_medicalcontrol.dto.OwnerRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.OwnerResponseDto;
import com.jr.sav_mvsc_medicalcontrol.dto.PhoneNumberDto;
import com.jr.sav_mvsc_medicalcontrol.services.OwnerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/sav/owner")
public class OwnerController {
    
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/disable")
    public ResponseEntity<List<OwnerResponseDto>> getAllDisableOwners() {
        return ResponseEntity.ok(ownerService.findAllDisabeOwners());
    }

    @GetMapping
    public ResponseEntity<List<OwnerResponseDto>> getAllActiveOwners() {
        return ResponseEntity.ok(ownerService.findAllActiveOwners());
    }

    @GetMapping("/id/{idOwner}")
    public ResponseEntity<?> getOwnerById(@PathVariable Long idOwner) {
        OwnerResponseDto owner = ownerService.findOwnerById(idOwner);
        return ResponseEntity.ok(owner);
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<?> getOwnerByDocumentNumber(@PathVariable String documentNumber) {
        OwnerResponseDto owner = ownerService.findOwnerByDocumentNumber(documentNumber);
        return ResponseEntity.ok(owner);
    }

    @PostMapping
    public ResponseEntity<?> saveInfoOwner(@Valid @RequestBody OwnerRequestDto owner) {
        OwnerResponseDto ownerCreated = ownerService.saveOwner(owner);
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerCreated);
    }

    @DeleteMapping("/id/{idOwner}")
    public ResponseEntity<?> deleteInfoOwner(@PathVariable Long idOwner) throws IllegalAccessException {
        ownerService.disableOwnerById(idOwner);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/updatePhone/{idOwner}")
    public ResponseEntity<?> updateIntoPhoneNumber(@PathVariable Long idOwner, @Valid @RequestBody PhoneNumberDto request) {
        ownerService.updatePhoneNumber(idOwner, request.getPhoneNumber());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/updateEmail/{idOwner}")
    public ResponseEntity<?> updateInfoEmail(@PathVariable Long idOwner, @Valid @RequestBody EmailDto request) {
        ownerService.updateEmail(idOwner, request.getEmail());
        return ResponseEntity.noContent().build();
    }
}
