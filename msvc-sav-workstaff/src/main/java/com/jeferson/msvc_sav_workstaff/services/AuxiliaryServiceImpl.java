package com.jeferson.msvc_sav_workstaff.services;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryDto;
import com.jeferson.msvc_sav_workstaff.mapper.AuxiliaryMapper;
import com.jeferson.msvc_sav_workstaff.models.Auxiliary;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.repositories.AuxiliaryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

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

    @Override
    public Optional<AuxiliaryDto> saveAuxiliary(AuxiliaryDto auxiliaryDto) {
        if (employeeService.findByDocumentNumber(auxiliaryDto.getDocumentNumber()).isPresent()) {
            return Optional.empty();
        }
        Auxiliary entity = auxMapper.toEntity(auxiliaryDto);
        entity.setRegistrationDate(LocalDate.now());
        return Optional.of(auxMapper.toDto(auxRepository.save(entity)));
    }
    
    @Override
    public Optional<AuxiliaryDto> findById(Long idEmployee){
        Optional<Auxiliary> optAuxiliary = auxRepository.findById(idEmployee);
        if (optAuxiliary.isEmpty()) {
            return Optional.empty();
        }
        return auxRepository.findById(idEmployee).map(auxMapper::toDto);
    }

    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email){
        if (auxRepository.findById(idEmployee).isEmpty()) {
            throw new EntityNotFoundException();
        }
        employeeService.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updatePhoneNumber(Long idEmployee, Long phoneNumber){
        if(auxRepository.findById(idEmployee).isEmpty()){
            throw new EntityNotFoundException();
        }
        employeeService.updateNumberPhone(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType){
        if (auxRepository.findById(idEmployee).isEmpty()) {
            throw new EntityNotFoundException();
        }
        employeeService.updateContractType(idEmployee, contractType);
    }

    @Override
    @Transactional
    public void updateWorkStatus(Long idEmployee, Boolean workStatus){
        if (auxRepository.findById(idEmployee).isEmpty()) {
            throw new EntityNotFoundException();
        }
        employeeService.updateWorkStatus(idEmployee, workStatus);
    }

    @Override
    public void delete (Long idEmployee){
        if (auxRepository.findById(idEmployee).isEmpty()) {
            throw new EntityNotFoundException();
        }
        employeeService.delete(idEmployee);
    }
}