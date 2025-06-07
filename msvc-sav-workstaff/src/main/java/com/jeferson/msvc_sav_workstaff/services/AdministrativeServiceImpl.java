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
    public Optional<AdministrativeDto> findByAdministrative(Long idEmployee) {
        Optional<Administrative> optAdministrative = administrativeRepository.findById(idEmployee);
        if (optAdministrative.isEmpty()) {
            return Optional.empty();
        }
        return administrativeRepository.findById(idEmployee).map(administrativeMapper::toDto);
    }

    @Override
    @Transactional
    public void uptAdministrativeWorkArea(Long idEmployee, String workArea) {
        if (administrativeRepository.findById(idEmployee).isEmpty()) {
            throw new EntityNotFoundException("El id: " + idEmployee + " no existe en el sistema");
        }
        administrativeRepository.updateWorkArea(idEmployee, workArea);
    }

    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email) {
        if (administrativeRepository.findById(idEmployee).isEmpty()) {
            throw new EntityNotFoundException("El id: " + idEmployee + " no existe en el sistema");
        }
        employeeService.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(long idEmployee, Long phoneNumber) {
        if (administrativeRepository.findById(idEmployee).isEmpty()) {
            throw new EntityNotFoundException("El id: " + idEmployee + " no existe en el sistema");
        }
        employeeService.updateNumberPhone(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType) {
        if (administrativeRepository.findById(idEmployee).isEmpty()) {
            throw new EntityNotFoundException("El id: " + idEmployee + " no existe en el sistema");
        }
        employeeService.updateContractType(idEmployee, contractType);
    }

    @Override
    @Transactional
    public void updateWorkStatus(Long idEmployee, Boolean workStratus){
        if(administrativeRepository.findById(idEmployee).isEmpty()){
            throw new EntityNotFoundException("El id: " + idEmployee + " no existe en el sistema");
        }
        employeeService.updateWorkStatus(idEmployee, workStratus);
    }

    @Override
    public void delete(Long idEmployee) {
        if (administrativeRepository.findById(idEmployee).isEmpty()) {
            throw new EntityNotFoundException("El id: " + idEmployee + " no existe en el sistema");
        }
        employeeService.delete(idEmployee);
    }

}
