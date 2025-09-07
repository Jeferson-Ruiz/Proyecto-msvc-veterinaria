package com.jeferson.msvc_sav_workstaff.services;

import java.util.List;
import com.jeferson.msvc_sav_workstaff.dto.AdministrativeRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.AdmistrativeResponseDto;
import com.jeferson.msvc_sav_workstaff.models.AdministrativeRoles;
import com.jeferson.msvc_sav_workstaff.models.ContractType;

public interface AdministrativeService {

    AdmistrativeResponseDto saveAdministrative(AdministrativeRequestDto administrative);

    List<AdmistrativeResponseDto> findAllAdmin();

    List<AdmistrativeResponseDto> findAllAdminDisabled();

    List<AdmistrativeResponseDto> findAllByRole(AdministrativeRoles administrativeRole);
    
    AdmistrativeResponseDto findAdminById (Long idEmployee);
    
    AdmistrativeResponseDto findAdminByDocumentNumber(String documentNumber);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);
    
    void updateRole(Long idEmployee, AdministrativeRoles admiRoles);

    void delete (Long idEmployee);

}
