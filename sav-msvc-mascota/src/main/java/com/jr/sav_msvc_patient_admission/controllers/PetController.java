package com.jr.sav_msvc_patient_admission.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jr.sav_msvc_patient_admission.dto.PetDto;
import com.jr.sav_msvc_patient_admission.dto.PetOwnerResponseDto;
import com.jr.sav_msvc_patient_admission.dto.PetResponseDto;
import com.jr.sav_msvc_patient_admission.services.PetService;

@RestController
@RequestMapping("api/sav/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<PetResponseDto>> getAllPets(){
        return ResponseEntity.ok(petService.findAllPets());
    }

    @GetMapping("/owners")
    public ResponseEntity<List<PetOwnerResponseDto>> getAllPetsWithOwners(){
        return ResponseEntity.ok(petService.findAllPetsWithOwners());
    }

    @PostMapping
    public ResponseEntity<?> saveInfoPet(@RequestBody PetDto petDto){
        PetOwnerResponseDto petCreated = petService.savePet(petDto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(petCreated);
    }

    @GetMapping("/id/{idPet}")
    public ResponseEntity<?> getPetById(@PathVariable Long idPet){
        PetResponseDto pet = petService.findPetById(idPet);
        return ResponseEntity.ok(pet);
    }

    @GetMapping("owner/{ownerNumber}")
    public ResponseEntity<?> getPetAndOwnerByNameAndDocument(@PathVariable Long ownerNumber, @RequestParam String name){
        PetOwnerResponseDto petDto = petService.findByNameAndOwnerNumber(name, ownerNumber);
        return ResponseEntity.ok(petDto);
    }

    @DeleteMapping("/{idPet}")
    public ResponseEntity<?> deleteInfoPet(@PathVariable Long idPet){
        petService.deletePetById(idPet);
        return ResponseEntity.noContent().build();
    }
} 