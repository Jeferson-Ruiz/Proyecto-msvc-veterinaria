package com.jr.sav_msvc_patient_admission.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jr.sav_msvc_patient_admission.dto.PetDto;
import com.jr.sav_msvc_patient_admission.services.PetService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("api/sav/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<PetDto>> getAllPets(){
        return ResponseEntity.ok(petService.findAllPets());
    }

    @PostMapping
    public ResponseEntity<?> saveInfoPet(@RequestBody PetDto petDto){
        Optional<PetDto> optPet = petService.savePet(petDto);
        if (optPet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Error, el paciente "+ petDto.getName() +" ya existe en el sistema");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(optPet.get());
    }

    @GetMapping("/{idPet}")
    public ResponseEntity<?> getPetById(@PathVariable Long idPet){
        Optional<PetDto> optPet = petService.findPetById(idPet);
        if (optPet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error, el paciente con el Id: "+ idPet +" no existe en el sistema");
        }
        return ResponseEntity.ok(optPet);
    }

    @GetMapping("owner/{ownerNumber}")
    public ResponseEntity<?> getPetByOwnerDocumentNumber(@PathVariable Long ownerNumber, @RequestBody String name){
        Optional<PetDto> optPet = petService.findByNameAndOwnerNumber(name, ownerNumber);
        if (optPet.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error, el paciente : "+ name +" no se encuentra vinculado con el propietario " + ownerNumber);
        }
        return ResponseEntity.ok(optPet);
    }

    @DeleteMapping("/{idPet}")
    public ResponseEntity<?> deleteInfoPet(@PathVariable Long idPet){
        try {
            petService.deletePetById(idPet);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El ID "+ idPet + " no fue encontrado");

        }
    }
} 