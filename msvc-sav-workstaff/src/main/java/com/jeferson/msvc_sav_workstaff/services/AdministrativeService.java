package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.dto.AdministrativeDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;

public interface AdministrativeService {

    Optional<AdministrativeDto> saveAdministrative(AdministrativeDto administrative);
    
    Optional<AdministrativeDto> findByAdministrative (Long idEmployee);

    void uptAdministrativeWorkArea(Long idEmployee, String workArea);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(long idEmployee, Long phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void updateWorkStatus(Long idEmployee, Boolean workStratus);

    void delete (Long idEmployee);

}
