package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.dto.AdministrativeDto;
import com.jeferson.msvc_sav_workstaff.models.Administrative;

public interface AdministrativeService {

    Optional<Administrative> saveAdministrative(AdministrativeDto administrative);

    void uptAdministrativeWorkArea(Long idEmployee, String workArea);

}
