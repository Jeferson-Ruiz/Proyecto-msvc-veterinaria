package com.jeferson.msvc_sav_workstaff.services;

import java.util.List;
import com.jeferson.msvc_sav_workstaff.dto.VetRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.VetResponseDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;

public interface VetService {
    VetResponseDto saveVet(VetRequestDto vetDto);

    List<VetResponseDto> findAllVet();

    VetResponseDto findById(Long idEmployee);

    VetResponseDto findAdminByDocumentNumber(String documentNumber);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void delete(Long idEmployee);
}