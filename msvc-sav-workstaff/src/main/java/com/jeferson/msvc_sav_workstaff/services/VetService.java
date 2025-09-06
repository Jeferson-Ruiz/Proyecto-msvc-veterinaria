package com.jeferson.msvc_sav_workstaff.services;

import java.util.List;
import com.jeferson.msvc_sav_workstaff.dto.VetRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.VetResponseDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.VetRoles;

public interface VetService {
    VetResponseDto saveVet(VetRequestDto vetDto);

    List<VetResponseDto> findAllVet();

    List<VetResponseDto> findAllDisabledVet();

    VetResponseDto findById(Long idEmployee);

    VetResponseDto findAdminByDocumentNumber(String documentNumber);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void updateRole(Long idEmployee, VetRoles vetRoles);

    void delete(Long idEmployee);
}