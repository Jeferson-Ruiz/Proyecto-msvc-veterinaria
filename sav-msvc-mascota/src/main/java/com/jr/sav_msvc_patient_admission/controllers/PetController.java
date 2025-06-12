package com.jr.sav_msvc_patient_admission.controllers;

import java.time.LocalDate;
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

import com.jr.sav_msvc_patient_admission.models.Pet;
import com.jr.sav_msvc_patient_admission.services.PetService;

@RestController
@RequestMapping("api/sav/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets(){
        return ResponseEntity.ok(petService.findAllPets());
    }

    @PostMapping
    public ResponseEntity<?> saveInfoPet(@RequestBody Pet pet){
        if(petService.findByNameAndOwerId(pet.getName(), pet.getOwerId()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("La mascota: "+ pet.getIdPet()+ " ya se encuentra registrada con el propietario "+ pet.getOwerId());
        }
        pet.setDateOfRecording(LocalDate.now());
        return new ResponseEntity<>(petService.savePet(pet), HttpStatus.CREATED);
    }


    @GetMapping("/{idPet}")
    public ResponseEntity<?> getPetById(@PathVariable Long idPet){
        if(petService.findPetById(idPet).isPresent()){
            return ResponseEntity.ok(petService.findPetById(idPet));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El ID: "+idPet + " No encontrado");
    }


    @DeleteMapping("/{idPet}")
    public ResponseEntity<?> deleteInfoPet(@PathVariable Long idPet){
        if (petService.findPetById(idPet).isPresent()) {
            petService.deletePetById(idPet);
            return ResponseEntity.noContent().build();
       }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El ID "+ idPet + " no fue encontrado");
    }
} 
