package com.jeferson.msvc_sav_workstaff.services;

import java.time.LocalDate;
import java.util.Optional;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.jeferson.msvc_sav_workstaff.dto.VetDto;
import com.jeferson.msvc_sav_workstaff.mapper.VetMapper;
import com.jeferson.msvc_sav_workstaff.models.Vet;
import com.jeferson.msvc_sav_workstaff.repositories.VetRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final EmployeeService employeeService;
    private final VetMapper vetMapper;

    public VetServiceImpl(VetRepository vetRepository, EmployeeService empRespository, VetMapper vetMapper) {
        this.vetRepository = vetRepository;
        this.employeeService = empRespository;
        this.vetMapper = vetMapper;
    }

    @Override
    public Optional<VetDto> saveVet(VetDto vetDto) {
        if (employeeService.findByDocumentNumber(vetDto.getDocumentNumber()).isPresent()) {
            return Optional.empty();
        }
        Vet entity = vetMapper.toEntity(vetDto);
        entity.setRegistrationDate(LocalDate.now());
        return Optional.of(vetMapper.toDto(vetRepository.save(entity)));
    }

    @Override
    public Optional<VetDto> findById(Long idEmployee){
        Optional<Vet> optVet = vetRepository.findById(idEmployee);
        if (optVet.isEmpty()) {
            return Optional.empty();
        }
        return vetRepository.findById(idEmployee).map(vetMapper::toDto);
    }

    @Override
    @Transactional
    public void updateEmail (Long idEmployee, String email){
        if (vetRepository.findById(idEmployee).isEmpty()){
            throw new EntityNotFoundException();
        }
        employeeService.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(Long idEmployee, Long phoneNumber){
        if (vetRepository.findById(idEmployee).isEmpty()){
            throw new EntityNotFoundException();
        }
        employeeService.updateNumberPhone(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType){
        if (vetRepository.findById(idEmployee).isEmpty()){
            throw new EntityNotFoundException();
        }
        employeeService.updateContractType(idEmployee, contractType);
    }

    @Override
    @Transactional
    public void updateWorkStatus(Long idEmployee, Boolean workStatus){
        if (vetRepository.findById(idEmployee).isEmpty()) {
            throw new EntityNotFoundException();
        }
        employeeService.updateWorkStatus(idEmployee, workStatus);
    }

    @Override
    public void delete(Long idEmployee){
        if (vetRepository.findById(idEmployee).isEmpty()){
            throw new EntityNotFoundException();
        }
        employeeService.delete(idEmployee);
    }


}