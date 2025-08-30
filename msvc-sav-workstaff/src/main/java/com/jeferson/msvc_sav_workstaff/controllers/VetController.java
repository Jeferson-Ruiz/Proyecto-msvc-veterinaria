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
import com.jeferson.msvc_sav_workstaff.dto.VetRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.VetResponseDto;
import com.jeferson.msvc_sav_workstaff.mapper.VetMapper;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.WorkArea;
import com.jeferson.msvc_sav_workstaff.services.VetService;

@RestController
@RequestMapping("api/sav/employee/vet")
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService, VetMapper vetMapper) {
        this.vetService = vetService;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoVet(@RequestBody VetRequestDto vetDto) {
        VetResponseDto vet = vetService.saveVet(vetDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(vet);
    }

    @GetMapping
    public ResponseEntity<?> findAllVets(){
        List<VetResponseDto> vets = vetService.findAllVet();
        return ResponseEntity.ok(vets);
    }

    @GetMapping("/id/{idEmployee}")
    public ResponseEntity<?> getVetById(@PathVariable Long idEmployee) {
        VetResponseDto vet = vetService.findById(idEmployee);
        return ResponseEntity.ok(vet);
    }

    @GetMapping("/document/{idEmployee}")
    public ResponseEntity<?> getVetBydocumentNumber(@PathVariable String documentNumber) {
        VetResponseDto vet = vetService.findAdminByDocumentNumber(documentNumber);
        return ResponseEntity.ok(vet);
    }

    @PatchMapping("/update-email/{idEmployee}")
    public ResponseEntity<?> updInfoEmail(@PathVariable Long idEmployee, @RequestBody String email) {
        vetService.updateEmail(idEmployee, email);
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
    public ResponseEntity<?> updWorkArea(@PathVariable Long idEmployee, @RequestBody WorkArea workArea) {
        vetService.updateWorkArea(idEmployee, workArea);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idEmployee}")
    public ResponseEntity<?> deleteVet(@PathVariable Long idEmployee) {
        vetService.delete(idEmployee);
        return ResponseEntity.noContent().build();
    }

}
