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
import com.jeferson.msvc.workstaff.dto.AuxiliaryRequestDto;
import com.jeferson.msvc.workstaff.dto.AuxiliaryResponseDto;
import com.jeferson.msvc.workstaff.dto.EmailRequestDto;
import com.jeferson.msvc.workstaff.dto.ActionInformationsRequestDto;
import com.jeferson.msvc.workstaff.dto.PhoneNumberRequestDto;
import com.jeferson.msvc.workstaff.mapper.AuxiliaryMapper;
import com.jeferson.msvc.workstaff.models.AuxiliaryRoles;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.services.AuxiliaryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auxiliary")
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

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getAllAuxiliary(@PathVariable EmployeeStatus status) {
        List<?> auxiliaries = auxService.findAllByStatus(status);
        return ResponseEntity.ok(auxiliaries);
    }

    @GetMapping("/role/{role}/status/{status}")
    public ResponseEntity<?> getAllByRole(@PathVariable AuxiliaryRoles role, @PathVariable EmployeeStatus status){
        List<?> auxiliaries = auxService.findAllByRoles(role, status);
        return ResponseEntity.ok(auxiliaries);
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<?> getAuxiliaryDocumentNumber(@PathVariable String documentNumber) {
        AuxiliaryResponseDto auxiliaryDto = auxService.findAdminByDocumentNumber(documentNumber);
        return ResponseEntity.ok(auxiliaryDto);
    }

    @GetMapping("/code/{employeeCode}")
    public ResponseEntity<?> getAuxiliaryByCode(@PathVariable String employeeCode) {
        AuxiliaryResponseDto auxiliaryDto = auxService.findAdminByCode(employeeCode);
        return ResponseEntity.ok(auxiliaryDto);
    }

    @PatchMapping("/update-email/{employeeCode}")
    public ResponseEntity<?> uptInfoEmail(@PathVariable String employeeCode, @Valid @RequestBody EmailRequestDto request) {
        auxService.updateEmail(employeeCode, request.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-number/{employeeCode}")
    public ResponseEntity<?> uptInfoPhoneNumber(@PathVariable String employeeCode, @Valid @RequestBody PhoneNumberRequestDto request) {
        auxService.updatePhoneNumber(employeeCode, request.getPhoneNumber());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-contract/{employeeCode}")
    public ResponseEntity<?> updInfoContractType(@PathVariable String employeeCode,@RequestBody ContractType contractType) {
        auxService.updateContractType(employeeCode, contractType);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-area/{employeeCode}")
    public ResponseEntity<?> updWorkArea(@PathVariable String employeeCode, @RequestBody AuxiliaryRoles auxiliaryRole) {
        auxService.updateRole(employeeCode, auxiliaryRole);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-status/{employeeCode}")
    public ResponseEntity<?> updateStatus(@PathVariable String employeeCode, @Valid @RequestBody EmployeeStatus status ) {
        auxService.updateEmployeeStatus(employeeCode, status);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/suspended/{employeeCode}")
    public ResponseEntity<?> suspended(@PathVariable String employeeCode, @Valid @RequestBody ActionInformationsRequestDto request ) {
        auxService.suspended(employeeCode, request.getDeletedBy(), request.getReason());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{employeeCode}")
    public ResponseEntity<?> deleteAuxiliary(@PathVariable String employeeCode, @Valid @RequestBody ActionInformationsRequestDto request ) {
        auxService.delete(employeeCode, request.getDeletedBy(), request.getReason());
        return ResponseEntity.noContent().build();
    }    
}
