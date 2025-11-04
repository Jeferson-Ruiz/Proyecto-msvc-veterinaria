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
import com.jeferson.msvc.workstaff.dto.InternResponseDto;
import com.jeferson.msvc.workstaff.dto.PhoneNumberRequestDto;
import com.jeferson.msvc.workstaff.dto.EmailRequestDto;
import com.jeferson.msvc.workstaff.dto.ActionInformationsRequestDto;
import com.jeferson.msvc.workstaff.dto.InternRequestDto;
import com.jeferson.msvc.workstaff.mapper.InternMapper;
import com.jeferson.msvc.workstaff.models.ContractType;
import com.jeferson.msvc.workstaff.models.EmployeeStatus;
import com.jeferson.msvc.workstaff.models.InternRoles;
import com.jeferson.msvc.workstaff.services.InternService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/intern")
public class InternController {

    private final InternService intService;

    public InternController(InternService intService, InternMapper intMapper) {
        this.intService = intService;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoIntern(@Valid @RequestBody InternRequestDto internDto) {
        InternResponseDto intern = intService.saveIntern(internDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(intern);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getAllInternsByStatus(@PathVariable EmployeeStatus status) {
        List<InternResponseDto> interns = intService.findAllByStatus(status);
        return ResponseEntity.ok(interns);
    }

    @GetMapping("/role/{role}/status/{status}")
    public ResponseEntity<?> getAllByRole(@PathVariable InternRoles role, @PathVariable EmployeeStatus status){
        List<InternResponseDto> interns = intService.findAllByRoleAndStatus(role, status);
        return ResponseEntity.ok(interns);
    }

    @GetMapping("/code/{employeeCode}")
    public ResponseEntity<?> getInternByCode(@PathVariable String employeeCode) {
        InternResponseDto intern = intService.findByCode(employeeCode);
        return ResponseEntity.ok(intern);
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<?> getInternByDocument(@PathVariable String documentNumber) {
        InternResponseDto intern = intService.findAdminByDocumentNumber(documentNumber);
        return ResponseEntity.ok(intern);
    }

    @PatchMapping("/update-email/{employeeCode}")
    public ResponseEntity<?> updInfoEmail(@PathVariable String employeeCode, @Valid @RequestBody EmailRequestDto request) {
        intService.updateEmail(employeeCode, request.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-number/{employeeCode}")
    public ResponseEntity<?> updInfoNumberPhone(@PathVariable String employeeCode, @Valid @RequestBody PhoneNumberRequestDto request) {
        intService.updateNumberPhone(employeeCode, request.getPhoneNumber());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-contract/{employeeCode}")
    public ResponseEntity<?> updContractType(@PathVariable String employeeCode, @RequestBody ContractType contract) {
        intService.updateContractType(employeeCode, contract);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-area/{employeeCode}")
    public ResponseEntity<?> updWorkArea(@PathVariable String employeeCode, @RequestBody InternRoles internRoles) {
        intService.updateRoles(employeeCode, internRoles);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-status/{employeeCode}")
    public ResponseEntity<?> updateStatus(@PathVariable String employeeCode, @Valid @RequestBody EmployeeStatus status ) {
        intService.updateEmployeeStatus(employeeCode, status);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/suspended/{employeeCode}")
    public ResponseEntity<?> suspended(@PathVariable String employeeCode, @Valid @RequestBody ActionInformationsRequestDto request ) {
        intService.suspended(employeeCode, request.getDeletedBy(), request.getReason());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{employeeCode}")
    public ResponseEntity<?> deleteIntern(@PathVariable String employeeCode, @Valid @RequestBody ActionInformationsRequestDto request ) {
        intService.delete(employeeCode, request.getDeletedBy(), request.getReason());
        return ResponseEntity.noContent().build();
    }
    
}
    