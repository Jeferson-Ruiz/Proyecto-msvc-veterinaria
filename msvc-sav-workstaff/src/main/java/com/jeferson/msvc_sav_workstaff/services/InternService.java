package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.dto.InternDto;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.Intern;

public interface InternService {

    Optional<Intern> saveIntern(InternDto internDto);

    void updateEmail(Long idEmployee, String email);

    void updateNumberPhone(Long idEmployee, Long phoneNumber);

    void updateContractType(Long idEmployee, ContractType contractType);

    void delete (Long idEmployee);

}