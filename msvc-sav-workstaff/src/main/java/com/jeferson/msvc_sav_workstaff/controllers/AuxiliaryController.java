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
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryResponseDto;
import com.jeferson.msvc_sav_workstaff.dto.EmailRequestDto;
import com.jeferson.msvc_sav_workstaff.mapper.AuxiliaryMapper;
import com.jeferson.msvc_sav_workstaff.models.AuxiliaryRoles;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.services.AuxiliaryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/sav/employee/auxiliary")
public class AuxiliaryController {

    private final AuxiliaryService auxService;

    public AuxiliaryController(AuxiliaryService auxService, AuxiliaryMapper auxMapper) {
        this.auxService = auxService;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoAuxiliary(@Valid @RequestBody AuxiliaryRequestDto auxiliaryDto) {
        AuxiliaryResponseDto auxiliary = auxService.saveAuxiliary(auxiliaryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(auxiliary);
    }

    @GetMapping
    public ResponseEntity<?> getAllAuxiliary() {
        List<AuxiliaryResponseDto> auxiliaries = auxService.findAllAuxiliary();
        return ResponseEntity.ok(auxiliaries);
    }

    @GetMapping("/disabled")
    public ResponseEntity<?> getAllDisabledAuxiliary() {
        List<AuxiliaryResponseDto> auxiliaries = auxService.findAllDisabledAuxiliary();
        return ResponseEntity.ok(auxiliaries);
    }


    @GetMapping("/allrole/{auxiliaryRole}")
    public ResponseEntity<?> getAllByRole(@PathVariable AuxiliaryRoles auxiliaryRole){
        List<AuxiliaryResponseDto> auxiliaries = auxService.findAllByRoles(auxiliaryRole);
        return ResponseEntity.ok(auxiliaries);
    }


    @GetMapping("/id/{idEmployee}")
    public ResponseEntity<?> getAuxiliaryById(@PathVariable Long idEmployee) {
        AuxiliaryResponseDto auxiliaryDto = auxService.findById(idEmployee);
        return ResponseEntity.ok(auxiliaryDto);
    }

    // error
    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<?> getAuxiliaryDocumentNumber(@PathVariable String documentNumber) {
        AuxiliaryResponseDto auxiliaryDto = auxService.findAdminByDocumentNumber(documentNumber);
        return ResponseEntity.ok(auxiliaryDto);
    }

    @PatchMapping("/update-email/{idEmployee}")
    public ResponseEntity<?> uptInfoEmail(@PathVariable Long idEmployee, @Valid @RequestBody EmailRequestDto request) {
        auxService.updateEmail(idEmployee, request.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-number/{idEmployee}")
    public ResponseEntity<?> uptInfoPhoneNumber(@PathVariable Long idEmployee, @RequestBody String phoneNumber) {
        auxService.updatePhoneNumber(idEmployee, phoneNumber);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-contract/{idEmployee}")
    public ResponseEntity<?> updInfoContractType(@PathVariable Long idEmployee,
            @RequestBody ContractType contractType) {
        auxService.updateContractType(idEmployee, contractType);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-area/{idEmployee}")
    public ResponseEntity<?> updWorkArea(@PathVariable Long idEmployee, @RequestBody AuxiliaryRoles auxiliaryRole) {
        auxService.updateRole(idEmployee, auxiliaryRole);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idEmployee}")
    public ResponseEntity<?> deleteAuxiliary(@PathVariable Long idEmployee) {
        auxService.delete(idEmployee);
        return ResponseEntity.noContent().build();
    }
}
