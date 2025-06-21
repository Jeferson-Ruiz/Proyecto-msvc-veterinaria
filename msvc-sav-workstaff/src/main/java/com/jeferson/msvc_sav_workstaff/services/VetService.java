package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.dto.VetDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;

public interface VetService {
    Optional<VetDto> saveVet(VetDto vetDto);

    Optional<VetDto> findById(Long idEmployee);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, Long phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void updateWorkStatus(Long idEmployee, Boolean workStatus);

    void delete(Long idEmployee);
}