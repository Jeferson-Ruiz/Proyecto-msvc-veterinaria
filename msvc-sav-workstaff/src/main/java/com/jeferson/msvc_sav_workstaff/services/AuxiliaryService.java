package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryDto;
import com.jeferson.msvc_sav_workstaff.models.Auxiliary;

public interface AuxiliaryService {
    
    Optional<Auxiliary> saveAuxiliary(AuxiliaryDto auxiliaryDto);

}
