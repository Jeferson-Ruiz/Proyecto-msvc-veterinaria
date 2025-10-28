package com.jr.msvc.medicalcontrol.services;

import java.time.LocalDate;
import java.util.List;
import com.jr.msvc.medicalcontrol.dto.vaccine.VaccineRequestDto;
import com.jr.msvc.medicalcontrol.dto.vaccine.VaccineResponseDto;

public interface VaccineService {

    VaccineResponseDto saveVaccine(VaccineRequestDto vaccineDto);

    List<VaccineResponseDto> findVaccinesIdPet(Long idPet);

    void updateNextApplicationDate(Long idVaccine, LocalDate newDate);
    
    void updateName(Long idVaccine, String name);
}
