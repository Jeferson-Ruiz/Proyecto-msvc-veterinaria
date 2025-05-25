package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.dto.InternDto;
import com.jeferson.msvc_sav_workstaff.models.Intern;

public interface InternService {

    Optional<Intern> saveIntern(InternDto internDto);

}