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
import com.jeferson.msvc_sav_workstaff.dto.InternRequestDto;
import com.jeferson.msvc_sav_workstaff.mapper.InternMapper;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.InternRoles;
import com.jeferson.msvc_sav_workstaff.services.InternService;

@RestController
@RequestMapping("api/sav/employee/intern")
public class InternController {

    private final InternService intService;

    public InternController(InternService intService, InternMapper intMapper) {
        this.intService = intService;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoIntern(@RequestBody InternRequestDto internDto) {
        InternResponseDto intern = intService.saveIntern(internDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(intern);
    }

    @GetMapping
    public ResponseEntity<?> getAllInterns() {
        List<InternResponseDto> interns = intService.findAllInter();
        return ResponseEntity.ok(interns);
    }

    @GetMapping("/disabled")
    public ResponseEntity<?> getAllDisabledInterns() {
        List<InternResponseDto> interns = intService.findAllDisabledInter();
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
    public ResponseEntity<?> updInfoEmail(@PathVariable Long idEmployee, @RequestBody String email) {
        intService.updateEmail(idEmployee, email);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-number/{idEmployee}")
    public ResponseEntity<?> updInfoNumberPhone(@PathVariable Long idEmployee, @RequestBody String numberPhone) {
        intService.updateNumberPhone(idEmployee, numberPhone);
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
    public ResponseEntity<?> deleteIntern(@PathVariable Long idEmployee) {
        intService.delete(idEmployee);
        return ResponseEntity.noContent().build();
    }
}
