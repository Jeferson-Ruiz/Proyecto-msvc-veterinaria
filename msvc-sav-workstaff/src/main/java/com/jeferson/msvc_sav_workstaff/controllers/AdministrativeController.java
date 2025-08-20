package com.jeferson.msvc_sav_workstaff.controllers;

import com.jeferson.msvc_sav_workstaff.dto.AdministrativeRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.AdmistrativeResponseDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.services.AdministrativeService;
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
@RequestMapping("api/sav/employee/administrative")
public class AdministrativeController {


    private final AdministrativeService adminService;

    public AdministrativeController(AdministrativeService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoAdministrative(@RequestBody AdministrativeRequestDto administrative) {
        AdmistrativeResponseDto administrativeDto =  adminService.saveAdministrative(administrative);
        return ResponseEntity.status(HttpStatus.CREATED).body(administrativeDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllAdministratives(){
        return ResponseEntity.ok(adminService.findAllAdmin());
    }

    @GetMapping("/{idEmployee}")
    public ResponseEntity<?> getAdministrativeById(@PathVariable Long idEmployee) {
        AdmistrativeResponseDto adminDto = adminService.findAdminById(idEmployee);
        return ResponseEntity.ok(adminDto);
    }

    @GetMapping("/{idEmployee}")
    public ResponseEntity<?> getAdministrativeByDocument(@PathVariable String documentNumber) {
        AdmistrativeResponseDto adminDto = adminService.findAdminByDocumentNumber(documentNumber);
        return ResponseEntity.ok(adminDto);
    }

    @PatchMapping("/update-email/{idEmployee}")
    public ResponseEntity<?> updInfoEmail(@PathVariable Long idEmployee, @RequestBody String email) {
        adminService.updateEmail(idEmployee, email);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-number/{idEmployee}")
    public ResponseEntity<?> updInfoNumberPhone(@PathVariable Long idEmployee, @RequestBody String PhoneNumber) {
        adminService.updateNumberPhone(idEmployee, PhoneNumber);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-contract/{idEmployee}")
    public ResponseEntity<?> updInfoContractType(@PathVariable Long idEmployee, @RequestBody ContractType contractType) {
        adminService.updateContractType(idEmployee, contractType);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idEmployee}")
    public ResponseEntity<?> deleteAdministrative(@PathVariable Long idEmployee) {
        adminService.delete(idEmployee);
        return ResponseEntity.noContent().build();
    }
}
