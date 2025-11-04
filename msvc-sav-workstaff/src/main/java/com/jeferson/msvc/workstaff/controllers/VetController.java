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
@RequestMapping("/vet")
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
    public ResponseEntity<?> getAllByStatus(@PathVariable EmployeeStatus status) {
        List<VetResponseDto> vets = vetService.findAllByStatus(status);
        return ResponseEntity.ok(vets);
    }

    @GetMapping("/role/{vetRole}/status/{status}")
    public ResponseEntity<?> getAllByRoleAndStatus(@PathVariable VetRoles vetRole, @PathVariable EmployeeStatus status){
        List<VetResponseDto> vets = vetService.findAllByRoleAndStatus(vetRole, status);
        return ResponseEntity.ok(vets);
    }

    @GetMapping("/code/{employeeCode}")
    public ResponseEntity<?> getVetByCode(@PathVariable String employeeCode) {
        VetResponseDto vet = vetService.findByCode(employeeCode);
        return ResponseEntity.ok(vet);
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<?> getVetBydocumentNumber(@PathVariable String documentNumber) {
        VetResponseDto vet = vetService.findAdminByDocumentNumber(documentNumber);
        return ResponseEntity.ok(vet);
    }

    @PatchMapping("/update-email/{employeeCode}")
    public ResponseEntity<?> updInfoEmail(@PathVariable String employeeCode, @Valid @RequestBody EmailRequestDto request) {
        vetService.updateEmail(employeeCode, request.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-number/{employeeCode}")
    public ResponseEntity<?> updInfoPhoneNumer(@PathVariable String employeeCode, @Valid @RequestBody PhoneNumberRequestDto request) {
        vetService.updateNumberPhone(employeeCode, request.getPhoneNumber());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-contract/{employeeCode}")
    public ResponseEntity<?> updInfoContractType(@PathVariable String employeeCode, @RequestBody ContractType contract) {
        vetService.updateContractType(employeeCode, contract);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-role/{employeeCode}")
    public ResponseEntity<?> updRole(@PathVariable String employeeCode, @RequestBody VetRoles vetRoles) {
        vetService.updateRole(employeeCode, vetRoles);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-status/{employeeCode}")
    public ResponseEntity<?> updateStatus(@PathVariable String employeeCode, @Valid @RequestBody EmployeeStatus status ) {
        vetService.updateEmployeeStatus(employeeCode, status);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/suspended/{employeeCode}")
    public ResponseEntity<?> suspendedVet(@PathVariable String employeeCode, @Valid @RequestBody ActionInformationsRequestDto request) {
        vetService.suspended(employeeCode, request.getDeletedBy(), request.getReason());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{employeeCode}")
    public ResponseEntity<?> deleteVet(@PathVariable String employeeCode, @Valid @RequestBody ActionInformationsRequestDto request) {
        vetService.delete(employeeCode, request.getDeletedBy(), request.getReason());
        return ResponseEntity.noContent().build();
    }
}
