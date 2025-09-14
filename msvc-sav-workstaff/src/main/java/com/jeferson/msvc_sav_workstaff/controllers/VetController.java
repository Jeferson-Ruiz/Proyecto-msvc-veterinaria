package com.jeferson.msvc_sav_workstaff.controllers;

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
import com.jeferson.msvc_sav_workstaff.dto.EmailRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.VetRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.VetResponseDto;
import com.jeferson.msvc_sav_workstaff.mapper.VetMapper;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.VetRoles;
import com.jeferson.msvc_sav_workstaff.services.VetService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/sav/employee/vet")
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

    @GetMapping
    public ResponseEntity<?> findAllVets() {
        List<VetResponseDto> vets = vetService.findAllVet();
        return ResponseEntity.ok(vets);
    }


    @GetMapping("/disabled")
    public ResponseEntity<?> findAllDisabledVets() {
        List<VetResponseDto> vets = vetService.findAllDisabledVet();
        return ResponseEntity.ok(vets);
    }

    @GetMapping("/allrole/{vetRole}")
    public ResponseEntity<?> findAllByRole(@PathVariable VetRoles vetRole){
        List<VetResponseDto> vets = vetService.findAllByRole(vetRole);
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
    public ResponseEntity<?> updInfoPhoneNumer(@PathVariable Long idEmployee, @RequestBody String phoneNumber) {
        vetService.updateNumberPhone(idEmployee, phoneNumber);
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
    public ResponseEntity<?> deleteVet(@PathVariable Long idEmployee) {
        vetService.delete(idEmployee);
        return ResponseEntity.noContent().build();
    }

}
