package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeferson.msvc_sav_workstaff.dto.AdministrativeDto;
import com.jeferson.msvc_sav_workstaff.mapper.AdministrativeMapper;
import com.jeferson.msvc_sav_workstaff.models.Administrative;
import com.jeferson.msvc_sav_workstaff.repositories.AdministrativeRepository;
import com.jeferson.msvc_sav_workstaff.repositories.EmployeeRespository;

@Service
public class AdministrativeServiceImpl implements AdministrativeService {

    private final AdministrativeRepository administrativeRepository;
    private final AdministrativeMapper administrativeMapper;
    private final EmployeeRespository employeeRespository;

    public AdministrativeServiceImpl(AdministrativeRepository administrativeRepository,
            AdministrativeMapper administrativeMapper,
            EmployeeRespository employeeRespository) {
        this.administrativeRepository = administrativeRepository;
        this.administrativeMapper = administrativeMapper;
        this.employeeRespository = employeeRespository;
    }

    @Override
    public Optional<Administrative> saveAdministrative(AdministrativeDto administrativeDto) {
        if (employeeRespository.findByDocumentNumber(administrativeDto.getDocumentNumber()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(administrativeRepository.save(administrativeMapper.toEntity(administrativeDto)));
    }

    @Override
    @Transactional
    public void uptAdministrativeWorkArea(Long idEmployee, String workArea) {
        if(employeeRespository.findById(idEmployee).isEmpty()){
            Optional.empty();
        }
        administrativeRepository.updateWorkArea(idEmployee, workArea);
    }
}