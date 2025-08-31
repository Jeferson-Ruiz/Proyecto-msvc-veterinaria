package com.jeferson.msvc_sav_workstaff.services;

import java.util.List;
import com.jeferson.msvc_sav_workstaff.dto.InternResponseDto;
import com.jeferson.msvc_sav_workstaff.dto.InternRequestDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.WorkArea;

public interface InternService {

    InternResponseDto saveIntern(InternRequestDto internDto);

    List<InternResponseDto> findAllInter();

    InternResponseDto findById(Long idEmployee);
    
    InternResponseDto findAdminByDocumentNumber(String documentNumber);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void updateWorkArea(Long idEmployee, WorkArea workArea);

    void delete (Long idEmployee);
}