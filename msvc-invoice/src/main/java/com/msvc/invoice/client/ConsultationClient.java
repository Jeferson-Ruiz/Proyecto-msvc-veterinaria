package com.msvc.invoice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.msvc.invoice.dto.ConsultationClientDto;

@FeignClient(name = "msvc-medicalcontrol")
public interface ConsultationClient {

    @GetMapping("consultation/code/{consultationCode}")
    ConsultationClientDto getConsultationByCode(@PathVariable("consultationCode") String consultationCode);


}
