package com.jr.msvc.medicalcontrol.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jr.msvc.medicalcontrol.dto.pet.PetRequestDto;
import com.jr.msvc.medicalcontrol.dto.pet.PetResponseDisableDto;
import com.jr.msvc.medicalcontrol.dto.pet.PetResponseDto;
import com.jr.msvc.medicalcontrol.dto.pet.PetWithOwnerResponseDto;
import com.jr.msvc.medicalcontrol.dto.removal.RemovalInfoRequestDto;
import com.jr.msvc.medicalcontrol.services.PetService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pet")
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
    
    @GetMapping("/disable")
    public ResponseEntity<List<PetResponseDisableDto>> getAllDisablePets() {
        return ResponseEntity.ok(petService.findAllDisablePets());
    }

    @GetMapping("/active")
    public ResponseEntity<List<PetWithOwnerResponseDto>> getAllActivePets() {
        return ResponseEntity.ok(petService.findAllActivesPets());
    }

    @GetMapping("/owner/{ownerNumber}/namepet/{name}")
    public ResponseEntity<?> getPetAndOwnerByNameAndDocument(@PathVariable String ownerNumber,
            @PathVariable String name) {
        PetWithOwnerResponseDto petDto = petService.findByNameAndOwnerNumber(name, ownerNumber);
        return ResponseEntity.ok(petDto);
    }

    @GetMapping("/code/{petCode}")
    public ResponseEntity<?> getPetByCode(@PathVariable String petCode){
        PetWithOwnerResponseDto petDto = petService.findPetByCode(petCode);
        return ResponseEntity.ok(petDto);
    }

    @GetMapping("/owner/document/{documentNumber}")
    public ResponseEntity<?> getPetsByOwnerDocument(@PathVariable String documentNumber) {
        List<PetResponseDto> petDto = petService.findPetsByOwnerDocument(documentNumber);
        return ResponseEntity.ok(petDto);
    }

    @GetMapping("/allspecie/{specie}")
    public ResponseEntity<?> getAllBySpecie(@PathVariable String specie){
        List<PetWithOwnerResponseDto> pets = petService.findAllBySpecie(specie);
        return ResponseEntity.ok(pets);
    }

    @DeleteMapping("/{petCode}")
    public ResponseEntity<?> deleteInfoPet(@PathVariable String petCode, @Valid @RequestBody RemovalInfoRequestDto request) {
        petService.disablePetByCode(petCode, request.getDeletedBy(), request.getReason());
        return ResponseEntity.noContent().build();
    }

}
