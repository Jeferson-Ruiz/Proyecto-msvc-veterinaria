package com.jeferson.msvc_sav_workstaff.services;

import java.util.List;
import com.jeferson.msvc_sav_workstaff.dto.InterResponseDto;
import com.jeferson.msvc_sav_workstaff.dto.InternRequestDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.WorkArea;

public interface InternService {

    InterResponseDto saveIntern(InternRequestDto internDto);

    List<InterResponseDto> findAllInter();

    InterResponseDto findById(Long idEmployee);
    
    InterResponseDto findAdminByDocumentNumber(String documentNumber);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void updateWorkArea(Long idEmployee, WorkArea workArea);

    void delete (Long idEmployee);
}