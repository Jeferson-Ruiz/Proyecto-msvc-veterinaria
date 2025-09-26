package com.jr.sav_mvsc_medicalcontrol.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.jr.sav_mvsc_medicalcontrol.dto.VetResponseDto;

@FeignClient(name = "msvc-sav-workstaff")
public interface VetClient {

    @GetMapping("vet/id/{idEmployee}")
    VetResponseDto getVetById(@PathVariable("idEmployee") Long idEmployee);

}
