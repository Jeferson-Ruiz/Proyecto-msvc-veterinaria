package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeferson.msvc_sav_workstaff.dto.AdministrativeDto;
import com.jeferson.msvc_sav_workstaff.mapper.AdministrativeMapper;
import com.jeferson.msvc_sav_workstaff.models.Administrative;
import com.jeferson.msvc_sav_workstaff.repositories.AdministrativeRepository;

@Service
public class AdministrativeServiceImpl implements AdministrativeService {

    private final AdministrativeRepository administrativeRepository;
    private final AdministrativeMapper administrativeMapper;
    private final EmployeeService employeeService;

    public AdministrativeServiceImpl(AdministrativeRepository administrativeRepository,
            AdministrativeMapper administrativeMapper,
            EmployeeService employeeService) {
        this.administrativeRepository = administrativeRepository;
        this.administrativeMapper = administrativeMapper;
        this.employeeService = employeeService;
    }

    @Override
    public Optional<Administrative> saveAdministrative(AdministrativeDto administrativeDto) {
        if (employeeService.findByDocumentNumber(administrativeDto.getDocumentNumber()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(administrativeRepository.save(administrativeMapper.toEntity(administrativeDto)));
    }

    @Override
    @Transactional
    public void uptAdministrativeWorkArea(Long idEmployee, String workArea) {
        if(employeeService.findById(idEmployee).isEmpty()){
            Optional.empty();
        }
        administrativeRepository.updateWorkArea(idEmployee, workArea);
    }
}