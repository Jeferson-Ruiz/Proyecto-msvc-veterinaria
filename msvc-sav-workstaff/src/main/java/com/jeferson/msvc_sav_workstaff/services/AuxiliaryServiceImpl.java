package com.jeferson.msvc_sav_workstaff.services;

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
    public Optional<Auxiliary> saveAuxiliary(AuxiliaryDto auxiliaryDto) {
        if (employeeService.findByDocumentNumber(auxiliaryDto.getDocumentNumber()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(auxRepository.save(auxMapper.toEntity(auxiliaryDto)));
    }

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
        if (employeeService.findById(idEmployee).isEmpty()) {
            throw new EntityNotFoundException("El id: "+ idEmployee +" no existe en el sistema");
        }
        employeeService.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updatePhoneNumber(Long idEmployee, Long phoneNumber){
        if(employeeService.findById(idEmployee).isEmpty()){
            throw new EntityNotFoundException("El id: "+ idEmployee +" no existe en el sistema");
        }
        employeeService.updateNumberPhone(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType){
        if (employeeService.findById(idEmployee).isEmpty()) {
            throw new EntityNotFoundException("El id: "+ idEmployee +" no existe en el sistema");
        }
        employeeService.updateContractType(idEmployee, contractType);
    }

    @Override
    public void delete (Long idEmployee){
        if (employeeService.findById(idEmployee).isEmpty()) {
            throw new EntityNotFoundException("El id: "+ idEmployee +" no existe en el sistema");
        }
        employeeService.delete(idEmployee);
    }
}