package com.jeferson.msvc_sav_workstaff.controllers;

import java.util.Optional;
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
import com.jeferson.msvc_sav_workstaff.dto.InternDto;
import com.jeferson.msvc_sav_workstaff.mapper.InternMapper;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.Intern;
import com.jeferson.msvc_sav_workstaff.services.InternService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api/sav/employee/intern")
public class InternController {

    private final InternService intService;
    private final InternMapper intMapper;

    public InternController(InternService intService, InternMapper intMapper) {
        this.intService = intService;
        this.intMapper = intMapper;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoIntern(InternDto internDto) {
        Optional<Intern> optInter = intService.saveIntern(internDto);

        if (optInter.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Usario ya existe en el sistema");
        }
        Intern internResgister = optInter.get();
        InternDto internResponse = intMapper.toDto(internResgister);
        return ResponseEntity.status(HttpStatus.CREATED).body(internResponse);
    }

    @GetMapping("/{idEmployee}")
    public ResponseEntity<?> getIntern(@PathVariable Long idEmployee){
        if (intService.findById(idEmployee).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("El id: "+idEmployee+" no existe en el sistema");
        }
        return ResponseEntity.ok(intService.findById(idEmployee));
    }


    @PatchMapping("/update/email/{idEmployee}")
    public ResponseEntity<?> updInfoEmail(@PathVariable Long idEmployee, String email) {
        try {
            intService.updateEmail(idEmployee, email);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/update/number/{idEmployee}")
    public ResponseEntity<?> updInfoNumberPhone(@PathVariable Long idEmployee, @RequestBody Long numberPhone) {
        try {
            intService.updateNumberPhone(idEmployee, numberPhone);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/update/contract/{idEmployee}")
    public ResponseEntity<?> updContractType(@PathVariable Long idEmployee, @RequestBody ContractType contract) {
        try {
            intService.updateContractType(idEmployee, contract);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idEmployee}")
    public ResponseEntity<?> deleteIntern(@PathVariable Long idEmployee) {
        try {
            intService.delete(idEmployee);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
