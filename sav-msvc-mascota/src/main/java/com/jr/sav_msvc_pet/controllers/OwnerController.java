package com.jr.sav_msvc_pet.controllers;

import java.util.List;
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
import com.jr.sav_msvc_pet.models.Owner;
import com.jr.sav_msvc_pet.services.OwnerService;

@RestController
@RequestMapping("api/sav/owner")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService){
        this.ownerService = ownerService;
    }

    @GetMapping
    public ResponseEntity<List<Owner>> getAllOwners(){
        return ResponseEntity.ok(ownerService.findAllOwners());
    }


    @GetMapping("/id/{idOwner}")
    public ResponseEntity<?> getOwnerById(@PathVariable Long idOwner){
        Optional<Owner> optoOwner = ownerService.findOwnerById(idOwner);

        if(optoOwner.isPresent()){
            return ResponseEntity.ok(optoOwner.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("El propietario con el ID : "+ idOwner + " no existe en el sistema");
    }
    

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<?> getOwnerByDocumentNumber(@PathVariable Long documentNumber){
        Optional<Owner> optoOwner = ownerService.findOwnerByDocumentNumber(documentNumber);
        if(optoOwner.isPresent()){
            return ResponseEntity.ok(ownerService.findOwnerByDocumentNumber(documentNumber));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Propietario con el N documento : "+ documentNumber +" no existe en el sistema");
    }


    @PostMapping
    public ResponseEntity<?> saveInfoOwner(@RequestBody Owner owner){
        if(ownerService.findOwnerByDocumentNumber(owner.getDocumentNumber()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Propietario con la identificacion: "+owner.getDocumentNumber() + " ya existe en el sistema");
        }
        return new ResponseEntity<>(ownerService.saveOwner(owner), HttpStatus.CREATED);
    }

    @DeleteMapping("{idOwner}")
    public ResponseEntity<?> deleteInfoOwner(@PathVariable Long idOwner){
        if(ownerService.findOwnerById(idOwner).isPresent()){
            ownerService.deleteOwnerById(idOwner);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Propietario con el ID : "+ idOwner +" no existe en el sistema");
    }
    

    @PatchMapping("/updatePhone/{idOwner}")
    public ResponseEntity<?> updateIntoPhoneNumber(@PathVariable Long idOwner, @RequestBody Long phoneNumber){
        if(ownerService.findOwnerById(idOwner).isPresent()){
            ownerService.updatePhoneNumber(idOwner, phoneNumber);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Propietario con el ID : "+ idOwner +" no se encuentra registrado en el sistema");    
        }
    

    @PatchMapping("/updateEmail/{idOwner}")
    public ResponseEntity<?> updateInfoEmail(@PathVariable Long idOwner, @RequestBody String email){
        if(ownerService.findOwnerById(idOwner).isPresent()){
            ownerService.updateEmail(idOwner, email);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Propietario con el ID : "+ idOwner +" no se encuentra registrado en el sistema");    
    }
}