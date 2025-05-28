package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryDto;
import com.jeferson.msvc_sav_workstaff.mapper.AuxiliaryMapper;
import com.jeferson.msvc_sav_workstaff.models.Auxiliary;
import com.jeferson.msvc_sav_workstaff.repositories.AuxiliaryRepository;

@Service
public class AuxiliaryServiceImpl implements AuxiliaryService {

    private final AuxiliaryRepository auxRepository;
    private final AuxiliaryMapper auxMapper;
    private final EmployeeService employeeService;

    public AuxiliaryServiceImpl(AuxiliaryRepository auxRepository,
            AuxiliaryMapper auxMapper,
            EmployeeService employeeService) {
        this.auxRepository = auxRepository;
        this.auxMapper = auxMapper;
        this.employeeService = employeeService;
    }

    public Optional<Auxiliary> saveAuxiliary(AuxiliaryDto auxiliaryDto) {
        if (employeeService.findByDocumentNumber(auxiliaryDto.getDocumentNumber()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(auxRepository.save(auxMapper.toEntity(auxiliaryDto)));
    }
}