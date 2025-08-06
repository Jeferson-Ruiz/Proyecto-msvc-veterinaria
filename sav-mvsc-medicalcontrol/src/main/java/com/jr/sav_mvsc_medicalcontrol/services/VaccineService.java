package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;
import com.jr.sav_mvsc_medicalcontrol.dto.VaccineDto;

public interface VaccineService {

    List<VaccineDto> findAllVaccines();

    VaccineDto saveVaccine(VaccineDto vaccineDto);

    VaccineDto findVaccineById(Long idVaccine);

    List<VaccineDto> findVaccinesIdPet(Long idPet);

    void deleteVaccine(Long idVaccine);
}
