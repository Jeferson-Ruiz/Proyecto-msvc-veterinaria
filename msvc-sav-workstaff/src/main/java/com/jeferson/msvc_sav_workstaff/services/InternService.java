package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.dto.InternDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;

public interface InternService {

    Optional<InternDto> saveIntern(InternDto internDto);

    Optional<InternDto> findById(Long idEmployee);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, Long phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void updateWorkStatus(Long idEmployee, Boolean workStatus);

    void delete (Long idEmployee);

}