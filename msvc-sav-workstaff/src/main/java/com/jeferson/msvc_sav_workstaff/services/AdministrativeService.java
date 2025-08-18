package com.jeferson.msvc_sav_workstaff.services;

import com.jeferson.msvc_sav_workstaff.dto.AdministrativeRequestDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;

public interface AdministrativeService {

    AdministrativeRequestDto saveAdministrative(AdministrativeRequestDto administrative);
    
    AdministrativeRequestDto findAdminById (Long idEmployee);
    
    AdministrativeRequestDto findAdminByDocumentNumber(String documentNumber);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void delete (Long idEmployee);

}
