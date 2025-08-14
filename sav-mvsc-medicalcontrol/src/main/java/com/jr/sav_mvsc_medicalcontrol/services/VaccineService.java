package com.jr.sav_mvsc_medicalcontrol.services;

import java.time.LocalDate;
import java.util.List;
import com.jr.sav_mvsc_medicalcontrol.dto.VaccineDto;
import com.jr.sav_mvsc_medicalcontrol.dto.VaccineResponseDto;

public interface VaccineService {

    List<VaccineResponseDto> findAllVaccines();

    VaccineResponseDto saveVaccine(VaccineDto vaccineDto);

    List<VaccineResponseDto> findVaccinesIdPet(Long idPet);

    void updateNextApplicationDate(Long idVaccine, LocalDate newDate);
    
    void updateName(Long idVaccine, String name);
}
