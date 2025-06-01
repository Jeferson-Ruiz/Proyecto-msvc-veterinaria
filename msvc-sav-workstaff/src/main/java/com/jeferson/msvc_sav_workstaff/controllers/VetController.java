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
import com.jeferson.msvc_sav_workstaff.dto.VetDto;
import com.jeferson.msvc_sav_workstaff.mapper.VetMapper;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.Vet;
import com.jeferson.msvc_sav_workstaff.services.VetService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api/sav/employee/vet")
public class VetController {

    private final VetService vetService;
    private final VetMapper vetMapper;

    public VetController(VetService vetService, VetMapper vetMapper) {
        this.vetService = vetService;
        this.vetMapper = vetMapper;
    }

    @PostMapping
    public ResponseEntity<?> saveInfoVet(@RequestBody VetDto vetDto) {
        Optional<Vet> optVet = vetService.saveVet(vetDto);

        if (optVet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("usuario ya existe en el sistema");
        }
        Vet vetRegist = optVet.get();
        VetDto vetResponse = vetMapper.toDto(vetRegist);
        return ResponseEntity.status(HttpStatus.CREATED).body(vetResponse);
    }

    @GetMapping("/{idEmployee}")
    public ResponseEntity<?> getVet(@PathVariable Long idEmployee){
        if (vetService.findById(idEmployee).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No existe el id: "+ idEmployee +" en el sistema");
        }
        return ResponseEntity.ok(vetService.findById(idEmployee));
    }

    @PatchMapping("/update/email/{idEmployee}")
    public ResponseEntity<?> updInfoEmail (@PathVariable Long idEmployee, @RequestBody String email){
        try {
            vetService.updateEmail(idEmployee, email);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/update/number/{idEmployee}")
    public ResponseEntity<?> updInfoPhoneNumer(@PathVariable Long idEmployee, Long phoneNumber){
        try {
            vetService.updateNumberPhone(idEmployee, phoneNumber);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/update/contract/{idEmployee}")
    public ResponseEntity<?> updInfoContractType(@PathVariable Long idEmployee, @RequestBody ContractType contract){
        try {
            vetService.updateContractType(idEmployee, contract);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idEmployee}")
    public ResponseEntity<?> deleteVet(@PathVariable Long idEmployee){
        try {
            vetService.delete(idEmployee);
            return ResponseEntity.noContent().build();            
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

}
