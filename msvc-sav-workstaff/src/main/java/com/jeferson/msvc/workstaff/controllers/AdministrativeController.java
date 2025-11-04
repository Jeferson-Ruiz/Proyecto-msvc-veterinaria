package com.jeferson.msvc.workstaff.controllers;

import com.jeferson.msvc.workstaff.dto.AdministrativeRequestDto;
import com.jeferson.msvc.workstaff.dto.AdmistrativeResponseDto;
import com.jeferson.msvc.workstaff.dto.EmailRequestDto;
import com.jeferson.msvc.workstaff.dto.PhoneNumberRequestDto;
import com.jeferson.msvc.workstaff.dto.ActionInformationsRequestDto;
import com.jeferson.msvc.workstaff.models.AdministrativeRoles;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.services.AdministrativeService;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/administrative")
public class AdministrativeController {


    private final AdministrativeService adminService;

    public AdministrativeController(AdministrativeService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoAdministrative(@Valid @RequestBody AdministrativeRequestDto administrative) {
        AdmistrativeResponseDto administrativeDto =  adminService.saveAdministrative(administrative);
        return ResponseEntity.status(HttpStatus.CREATED).body(administrativeDto);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getAllAdministratives(@PathVariable EmployeeStatus status){
        return ResponseEntity.ok(adminService.findAllByStatus(status));
    }

    @GetMapping("/role/{role}/status/{status}")
    public ResponseEntity<?> getAllbyRoles(@PathVariable AdministrativeRoles role, @PathVariable EmployeeStatus status){
        return ResponseEntity.ok(adminService.findAllByRole(role, status));
    }

    @GetMapping("/code/{employeeCode}")
    public ResponseEntity<?> getAdministrativeByCode(@PathVariable String employeeCode) {
        AdmistrativeResponseDto adminDto = adminService.findAdminByCode(employeeCode);
        return ResponseEntity.ok(adminDto);
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<?> getAdministrativeByDocument(@PathVariable String documentNumber) {
        AdmistrativeResponseDto adminDto = adminService.findAdminByDocumentNumber(documentNumber);
        return ResponseEntity.ok(adminDto);
    }

    @PatchMapping("/update-email/{employeeCode}")
    public ResponseEntity<?> updInfoEmail(@PathVariable String employeeCode, @Valid @RequestBody EmailRequestDto request) {
        adminService.updateEmail(employeeCode, request.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-number/{employeeCode}")
    public ResponseEntity<?> updInfoNumberPhone(@PathVariable String employeeCode, @Valid @RequestBody PhoneNumberRequestDto request) {
        adminService.updateNumberPhone(employeeCode, request.getPhoneNumber());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-contract/{employeeCode}")
    public ResponseEntity<?> updInfoContractType(@PathVariable String employeeCode, @RequestBody ContractType contractType) {
        adminService.updateContractType(employeeCode, contractType);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-area/{employeeCode}")
    public ResponseEntity<?> updworkArea(@PathVariable String employeeCode, @RequestBody AdministrativeRoles admiRoles) {
        adminService.updateRole(employeeCode, admiRoles);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/update-status/{employeeCode}")
    public ResponseEntity<?> updateStatus(@PathVariable String employeeCode, @Valid @RequestBody EmployeeStatus status ) {
        adminService.updateEmployeeStatus(employeeCode, status);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/suspended/{employeeCode}")
    public ResponseEntity<?> suspendAdministrative(@PathVariable String employeeCode, @Valid @RequestBody ActionInformationsRequestDto request ) {
        adminService.suspended(employeeCode, request.getDeletedBy(), request.getReason());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{employeeCode}")
    public ResponseEntity<?> deleteAdministrative(@PathVariable String employeeCode, @Valid @RequestBody ActionInformationsRequestDto request ) {
        adminService.delete(employeeCode, request.getDeletedBy(), request.getReason());
        return ResponseEntity.noContent().build();
    }
}
