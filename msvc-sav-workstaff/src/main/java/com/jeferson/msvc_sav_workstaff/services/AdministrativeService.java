package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.models.Administrative;

public interface AdministrativeService {
    Optional<Administrative> findAdmistrativeById(Long idEmployee);

    Administrative saveAdministrative(Administrative administrative);

    void updateAdministrativeWorkArea(Long idEmployee, String workArea);

}
