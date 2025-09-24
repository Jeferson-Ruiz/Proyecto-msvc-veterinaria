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
import com.jeferson.msvc_sav_workstaff.dto.InternResponseDto;
import com.jeferson.msvc_sav_workstaff.dto.PhoneNumberRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.ActionInformationsRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.EmailRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.InternRequestDto;
import com.jeferson.msvc_sav_workstaff.mapper.InternMapper;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.EmployeeStatus;
import com.jeferson.msvc_sav_workstaff.models.InternRoles;
import com.jeferson.msvc_sav_workstaff.services.InternService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/sav/employee/intern")
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
        List<InternResponseDto> interns = intService.findAllByRole(role, status);
        return ResponseEntity.ok(interns);
    }

    @GetMapping("/id/{idEmployee}")
    public ResponseEntity<?> getInternById(@PathVariable Long idEmployee) {
        InternResponseDto intern = intService.findById(idEmployee);
        return ResponseEntity.ok(intern);
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<?> getInternByDocument(@PathVariable String documentNumber) {
        InternResponseDto intern = intService.findAdminByDocumentNumber(documentNumber);
        return ResponseEntity.ok(intern);
    }

    @PatchMapping("/update-email/{idEmployee}")
    public ResponseEntity<?> updInfoEmail(@PathVariable Long idEmployee, @Valid @RequestBody EmailRequestDto request) {
        intService.updateEmail(idEmployee, request.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-number/{idEmployee}")
    public ResponseEntity<?> updInfoNumberPhone(@PathVariable Long idEmployee, @Valid @RequestBody PhoneNumberRequestDto request) {
        intService.updateNumberPhone(idEmployee, request.getPhoneNumber());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-contract/{idEmployee}")
    public ResponseEntity<?> updContractType(@PathVariable Long idEmployee, @RequestBody ContractType contract) {
        intService.updateContractType(idEmployee, contract);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-area/{idEmployee}")
    public ResponseEntity<?> updWorkArea(@PathVariable Long idEmployee, @RequestBody InternRoles internRoles) {
        intService.updateRoles(idEmployee, internRoles);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idEmployee}")
    public ResponseEntity<?> deleteIntern(@PathVariable Long idEmployee, @Valid @RequestBody ActionInformationsRequestDto request ) {
        intService.delete(idEmployee, request.getDeletedBy(), request.getReason());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/suspended/{idEmployee}")
    public ResponseEntity<?> suspended(@PathVariable Long idEmployee, @Valid @RequestBody ActionInformationsRequestDto request ) {
        intService.suspended(idEmployee, request.getDeletedBy(), request.getReason());
        return ResponseEntity.noContent().build();
    }
}
    