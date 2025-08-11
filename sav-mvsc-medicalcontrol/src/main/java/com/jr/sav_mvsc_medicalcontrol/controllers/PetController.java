package com.jr.sav_mvsc_medicalcontrol.controllers;

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

import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetOwnerResponseDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetResponseDto;
import com.jr.sav_mvsc_medicalcontrol.services.PetService;

@RestController
@RequestMapping("api/sav/pet")
public class PetController {
    
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }
    
    @PostMapping
    public ResponseEntity<?> saveInfoPet(@RequestBody PetDto petDto) {
        PetOwnerResponseDto petCreated = petService.savePet(petDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(petCreated);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<PetResponseDto>> getAllPets() {
        return ResponseEntity.ok(petService.findAllPets());
    }
    
    @GetMapping("/disable")
    public ResponseEntity<List<PetOwnerResponseDto>> getAllDisablePets() {
        return ResponseEntity.ok(petService.findAllDisablePets());
    }

    @GetMapping("/info-owners")
    public ResponseEntity<List<PetOwnerResponseDto>> getAllActivePets() {
        return ResponseEntity.ok(petService.findAllActivesPets());
    }

    @GetMapping("/id/{idPet}")
    public ResponseEntity<?> getPetById(@PathVariable Long idPet) {
        PetResponseDto pet = petService.findPetById(idPet);
        return ResponseEntity.ok(pet);
    }

    @GetMapping("/owner/{ownerNumber}")
    public ResponseEntity<?> getPetAndOwnerByNameAndDocument(@PathVariable Long ownerNumber,
            @RequestParam String name) {
        PetOwnerResponseDto petDto = petService.findByNameAndOwnerNumber(name, ownerNumber);
        return ResponseEntity.ok(petDto);
    }

    @GetMapping("/owner/document/{documentNumber}")
    public ResponseEntity<?> getPetsByOwnerDocument(@PathVariable Long documentNumber) {
        List<PetResponseDto> petDto = petService.findPetsByOwner(documentNumber);
        return ResponseEntity.ok(petDto);
    }

    @DeleteMapping("/{idPet}")
    public ResponseEntity<?> deleteInfoPet(@PathVariable Long idPet) {
        petService.disablePetById(idPet);;
        return ResponseEntity.noContent().build();
    }

}
