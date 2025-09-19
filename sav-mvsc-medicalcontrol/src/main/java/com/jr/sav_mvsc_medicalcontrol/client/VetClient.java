package com.jr.sav_mvsc_medicalcontrol.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.jr.sav_mvsc_medicalcontrol.dto.VetDto;

@FeignClient(name = "msvc-sav-workstaff")
public interface VetClient {

    @GetMapping("api/sav/employee/vet/id/{idEmployee}")
    VetDto getVetById(@PathVariable("idEmployee") Long idEmployee);

}
