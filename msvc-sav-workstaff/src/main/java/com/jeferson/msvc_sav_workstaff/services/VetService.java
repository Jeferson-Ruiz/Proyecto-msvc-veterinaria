package com.jeferson.msvc_sav_workstaff.services;

import com.jeferson.msvc_sav_workstaff.dto.VetRequestDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;

public interface VetService {
    VetRequestDto saveVet(VetRequestDto vetDto);

    VetRequestDto findById(Long idEmployee);

    VetRequestDto findAdminByDocumentNumber(String documentNumber);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, String phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void delete(Long idEmployee);
}