package com.jeferson.msvc.workstaff.controllers;

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

import com.jeferson.msvc.workstaff.dto.EmailRequestDto;
import com.jeferson.msvc.workstaff.dto.ActionInformationsRequestDto;
import com.jeferson.msvc.workstaff.dto.PhoneNumberRequestDto;
import com.jeferson.msvc.workstaff.dto.VetRequestDto;
import com.jeferson.msvc.workstaff.dto.VetResponseDto;
import com.jeferson.msvc.workstaff.mapper.VetMapper;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.models.VetRoles;
import com.jeferson.msvc.workstaff.services.VetService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("vet")
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService, VetMapper vetMapper) {
        this.vetService = vetService;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoVet(@Valid @RequestBody VetRequestDto vetDto) {
        VetResponseDto vet = vetService.saveVet(vetDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(vet);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> findAllByStatus(@PathVariable EmployeeStatus status) {
        List<VetResponseDto> vets = vetService.findAllByStatus(status);
        return ResponseEntity.ok(vets);
    }

    @GetMapping("/role/{vetRole}/status/{status}")
    public ResponseEntity<?> findAllByRole(@PathVariable VetRoles vetRole, @PathVariable EmployeeStatus status){
        List<VetResponseDto> vets = vetService.findAllByRole(vetRole, status);
        return ResponseEntity.ok(vets);
    }

    @GetMapping("/id/{idEmployee}")
    public ResponseEntity<?> getVetById(@PathVariable Long idEmployee) {
        VetResponseDto vet = vetService.findById(idEmployee);
        return ResponseEntity.ok(vet);
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<?> getVetBydocumentNumber(@PathVariable String documentNumber) {
        VetResponseDto vet = vetService.findAdminByDocumentNumber(documentNumber);
        return ResponseEntity.ok(vet);
    }

    @PatchMapping("/update-email/{idEmployee}")
    public ResponseEntity<?> updInfoEmail(@PathVariable Long idEmployee, @Valid @RequestBody EmailRequestDto request) {
        vetService.updateEmail(idEmployee, request.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-number/{idEmployee}")
    public ResponseEntity<?> updInfoPhoneNumer(@PathVariable Long idEmployee, @Valid @RequestBody PhoneNumberRequestDto request) {
        vetService.updateNumberPhone(idEmployee, request.getPhoneNumber());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-contract/{idEmployee}")
    public ResponseEntity<?> updInfoContractType(@PathVariable Long idEmployee, @RequestBody ContractType contract) {
        vetService.updateContractType(idEmployee, contract);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-area/{idEmployee}")
    public ResponseEntity<?> updWorkArea(@PathVariable Long idEmployee, @RequestBody VetRoles vetRoles) {
        vetService.updateRole(idEmployee, vetRoles);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idEmployee}")
    public ResponseEntity<?> deleteVet(@PathVariable Long idEmployee, @Valid @RequestBody ActionInformationsRequestDto request) {
        vetService.delete(idEmployee, request.getDeletedBy(), request.getReason());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/suspended/{idEmployee}")
    public ResponseEntity<?> suspendedVet(@PathVariable Long idEmployee, @Valid @RequestBody ActionInformationsRequestDto request) {
        vetService.suspended(idEmployee, request.getDeletedBy(), request.getReason());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-status/{idEmployee}")
    public ResponseEntity<?> updateStatus(@PathVariable Long idEmployee, @Valid @RequestBody EmployeeStatus status ) {
        vetService.updateEmployeeStatus(idEmployee, status);
        return ResponseEntity.noContent().build();
    }
}
