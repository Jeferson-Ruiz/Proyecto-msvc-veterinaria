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
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetRequestDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetWithOwnerResponseDto;
import com.jr.sav_mvsc_medicalcontrol.dto.pet.PetResponseDto;
import com.jr.sav_mvsc_medicalcontrol.services.PetService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/sav/pet")
public class PetController {
    
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }
    
    @PostMapping
    public ResponseEntity<?> saveInfoPet(@Valid @RequestBody PetRequestDto petDto) {
        PetWithOwnerResponseDto petCreated = petService.savePet(petDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(petCreated);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<PetResponseDto>> getAllPets() {
        return ResponseEntity.ok(petService.findAllPets());
    }
    
    @GetMapping("/disable")
    public ResponseEntity<List<PetWithOwnerResponseDto>> getAllDisablePets() {
        return ResponseEntity.ok(petService.findAllDisablePets());
    }

    @GetMapping("/info-owners")
    public ResponseEntity<List<PetWithOwnerResponseDto>> getAllActivePets() {
        return ResponseEntity.ok(petService.findAllActivesPets());
    }

    @GetMapping("/id/{idPet}")
    public ResponseEntity<?> getPetById(@PathVariable Long idPet) {
        PetResponseDto pet = petService.findPetById(idPet);
        return ResponseEntity.ok(pet);
    }

    @GetMapping("/owner/{ownerNumber}")
    public ResponseEntity<?> getPetAndOwnerByNameAndDocument(@PathVariable String ownerNumber,
            @RequestParam String name) {
        PetWithOwnerResponseDto petDto = petService.findByNameAndOwnerNumber(name, ownerNumber);
        return ResponseEntity.ok(petDto);
    }

    @GetMapping("/owner/document/{documentNumber}")
    public ResponseEntity<?> getPetsByOwnerDocument(@PathVariable String documentNumber) {
        List<PetResponseDto> petDto = petService.findPetsByOwnerDocument(documentNumber);
        return ResponseEntity.ok(petDto);
    }

    @DeleteMapping("/{idPet}")
    public ResponseEntity<?> deleteInfoPet(@PathVariable Long idPet) {
        petService.disablePetById(idPet);;
        return ResponseEntity.noContent().build();
    }

}
