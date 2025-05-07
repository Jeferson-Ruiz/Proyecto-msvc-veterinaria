package com.jr.sav_mvsc_medicalcontrol.services;

import java.util.List;
import java.util.Optional;
import com.jr.sav_mvsc_medicalcontrol.models.Vaccine;

public interface VaccineService {

    List<Vaccine> findAllVaccines();

    Vaccine saveVaccine(Vaccine vaccine);

    Optional<Vaccine> findVaccineById(Long idVaccine);

    void deleteVaccine(Long idVaccine);
}
