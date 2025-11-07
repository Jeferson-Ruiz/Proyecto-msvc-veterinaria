package com.jr.msvc.medicalcontrol.services;

import java.time.LocalDate;
import java.util.List;
import com.jr.msvc.medicalcontrol.dto.vaccine.VaccineRequestDto;
import com.jr.msvc.medicalcontrol.dto.vaccine.VaccineResponseDto;

public interface VaccineService {

    VaccineResponseDto saveVaccine(VaccineRequestDto vaccineDto);

    List<VaccineResponseDto> findVaccinesPetCode(String petCode);

    void updateNextApplicationDate(String vaccineCode, LocalDate newDate);
    
    void updateName(String vaccineCode, String newName);
}
