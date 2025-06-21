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
import com.jeferson.msvc_sav_workstaff.services.InternService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api/sav/employee/intern")
public class InternController {

    private final InternService intService;

    public InternController(InternService intService, InternMapper intMapper) {
        this.intService = intService;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoIntern(@RequestBody InternDto internDto) {
        Optional<InternDto> optInter = intService.saveIntern(internDto);

        if (optInter.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error de registro\nusuario ya existe en el sistema");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(optInter.get());
    }

    @GetMapping("/{idEmployee}")
    public ResponseEntity<?> getIntern(@PathVariable Long idEmployee) {
        if (intService.findById(idEmployee).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe praticante con el id: " + idEmployee + " en el sistema");
        }
        return ResponseEntity.ok(intService.findById(idEmployee));
    }

    @PatchMapping("/update/email/{idEmployee}")
    public ResponseEntity<?> updInfoEmail(@PathVariable Long idEmployee, @RequestBody String email) {
        try {
            intService.updateEmail(idEmployee, email);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error de actualizacion\nNo existe praticante con el id: " + idEmployee);
        }
    }

    @PatchMapping("/update/number/{idEmployee}")
    public ResponseEntity<?> updInfoNumberPhone(@PathVariable Long idEmployee, @RequestBody Long numberPhone) {
        try {
            intService.updateNumberPhone(idEmployee, numberPhone);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error de actualizacion\nNo existe praticante con el id: " + idEmployee);
        }
    }

    @PatchMapping("/update/contract/{idEmployee}")
    public ResponseEntity<?> updContractType(@PathVariable Long idEmployee, @RequestBody ContractType contract) {
        try {
            intService.updateContractType(idEmployee, contract);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error de actualizacion\nNo existe praticante con el id: " + idEmployee);
        }
    }

    @PatchMapping("/update/status/{idEmployee}")
    public ResponseEntity<?> updateInfoWorkStatus(@PathVariable Long idEmployee, @RequestBody Boolean workStatus){
        try {
            intService.updateWorkStatus(idEmployee, workStatus);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error de actualizacion\nNo existe praticante con el id: " + idEmployee);
        }
    }

    @DeleteMapping("/{idEmployee}")
    public ResponseEntity<?> deleteIntern(@PathVariable Long idEmployee) {
        try {
            intService.delete(idEmployee);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error de actualizacion\nNo existe praticante con el id: " + idEmployee);
        }
    }
}
