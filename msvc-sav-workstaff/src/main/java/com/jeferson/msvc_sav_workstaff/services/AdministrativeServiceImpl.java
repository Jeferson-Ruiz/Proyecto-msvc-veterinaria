package com.jeferson.msvc_sav_workstaff.services;

import java.util.Optional;

import com.jeferson.msvc_sav_workstaff.models.ContractType;
import jakarta.persistence.EntityNotFoundException;
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

    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email){
        if (employeeService.findById(idEmployee).isEmpty()){
            throw new EntityNotFoundException("El id: "+ idEmployee +" no existe en el sistema");
        }
        employeeService.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(long idEmployee, Long phoneNumber){
        if(employeeService.findById(idEmployee).isEmpty()){
            throw new EntityNotFoundException("El id: "+ idEmployee +" no existe en el sistema");
        }
        employeeService.updateNumberPhone(idEmployee, phoneNumber);
    }
    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType){
        if (employeeService.findById(idEmployee).isEmpty()){
            throw new EntityNotFoundException("El id: "+ idEmployee +" no existe en el sistema");
        }
        employeeService.updateContractType(idEmployee, contractType);
    }

    @Override
    public void delete (Long idEmployee){
        if (employeeService.findById(idEmployee).isEmpty()){
            throw new EntityNotFoundException("El id: "+ idEmployee +" no existe en el sistema");
        }
        employeeService.delete(idEmployee);
    }

}
