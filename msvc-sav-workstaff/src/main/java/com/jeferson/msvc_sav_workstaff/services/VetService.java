package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.dto.VetDto;
import com.jeferson.msvc_sav_workstaff.models.Vet;

public interface VetService {
    Optional<Vet> saveVet(VetDto vetDto);
}
