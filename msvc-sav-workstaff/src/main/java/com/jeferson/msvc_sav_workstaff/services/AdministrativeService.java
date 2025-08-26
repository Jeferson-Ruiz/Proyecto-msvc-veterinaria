package com.jeferson.msvc_sav_workstaff.services;

import java.util.List;
import com.jeferson.msvc_sav_workstaff.dto.AdministrativeRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.AdmistrativeResponseDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.WorkArea;

public interface AdministrativeService {

    AdmistrativeResponseDto saveAdministrative(AdministrativeRequestDto administrative);

    List<AdmistrativeResponseDto> findAllAdmin();
    
    AdmistrativeResponseDto findAdminById (Long idEmployee);
    
    AdmistrativeResponseDto findAdminByDocumentNumber(String documentNumber);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);
    
    void updateWorkArea(Long idEmployee, WorkArea workArea);

    void delete (Long idEmployee);

}
