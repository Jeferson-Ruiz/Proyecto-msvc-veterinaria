package com.jr.msvc.medicalcontrol.controllers;

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
import com.jr.msvc.medicalcontrol.dto.owner.OwnerRequestDto;
import com.jr.msvc.medicalcontrol.dto.owner.OwnerResponseDto;
import com.jr.msvc.medicalcontrol.dto.update.EmailDto;
import com.jr.msvc.medicalcontrol.dto.update.PhoneNumberDto;
import com.jr.msvc.medicalcontrol.services.OwnerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoOwner(@Valid @RequestBody OwnerRequestDto owner) {
        OwnerResponseDto ownerCreated = ownerService.saveOwner(owner);
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerCreated);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OwnerResponseDto>> getAllDisableOwners(@PathVariable boolean status) {
        return ResponseEntity.ok(ownerService.findAllByStatus(status));
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<?> getOwnerByDocumentNumber(@PathVariable String documentNumber) {
        OwnerResponseDto owner = ownerService.findOwnerByDocumentNumber(documentNumber);
        return ResponseEntity.ok(owner);
    }

    @PatchMapping("/update-phone/{document}")
    public ResponseEntity<?> updateIntoPhoneNumber(@PathVariable String document, @Valid @RequestBody PhoneNumberDto request) {
        ownerService.updatePhoneNumber(document, request.getPhoneNumber());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-email/{document}")
    public ResponseEntity<?> updateInfoEmail(@PathVariable String document, @Valid @RequestBody EmailDto request) {
        ownerService.updateEmail(document, request.getEmail());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/document/{document}")
    public ResponseEntity<?> deleteInfoOwner(@PathVariable String document) throws IllegalAccessException {
        ownerService.disableOwnerByDocument(document);
        return ResponseEntity.noContent().build();
    }
}
