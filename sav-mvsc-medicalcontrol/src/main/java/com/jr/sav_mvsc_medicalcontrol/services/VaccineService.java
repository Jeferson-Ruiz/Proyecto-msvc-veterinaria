package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;
import java.util.Optional;
import com.jr.sav_mvsc_medicalcontrol.dto.VaccineDto;

public interface VaccineService {

    List<VaccineDto> findAllVaccines();

    Optional<VaccineDto> saveVaccine(VaccineDto vaccineDto);

    Optional<VaccineDto> findVaccineById(Long idVaccine);

    void deleteVaccine(Long idVaccine);
}
