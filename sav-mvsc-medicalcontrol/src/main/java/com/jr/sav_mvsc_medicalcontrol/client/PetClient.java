package com.jr.sav_mvsc_medicalcontrol.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.jr.sav_mvsc_medicalcontrol.dto.PetDto;

@FeignClient(name = "sav-msvc-mascota", path = "/api/sav/pet")
public interface PetClient {

    @GetMapping("{idPet}")
    PetDto getPetById(@PathVariable Long idPet);
    
}
