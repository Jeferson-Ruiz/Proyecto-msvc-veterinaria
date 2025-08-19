package com.jeferson.msvc_sav_workstaff.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.AuxiliaryResponseDto;
import com.jeferson.msvc_sav_workstaff.mapper.AuxiliaryMapper;
import com.jeferson.msvc_sav_workstaff.models.Auxiliary;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.repositories.AuxiliaryRepository;
import com.jeferson.msvc_sav_workstaff.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AuxiliaryServiceImpl implements AuxiliaryService {

    private final AuxiliaryRepository auxRepository;
    private final AuxiliaryMapper auxMapper;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRespo;

    public AuxiliaryServiceImpl(AuxiliaryRepository auxRepository,
            AuxiliaryMapper auxMapper,
            EmployeeService employeeService,
            EmployeeRepository employeeRespo) {
        this.auxRepository = auxRepository;
        this.auxMapper = auxMapper;
        this.employeeService = employeeService;
        this.employeeRespo = employeeRespo;
    }

    @Override
    public AuxiliaryResponseDto saveAuxiliary(AuxiliaryRequestDto auxiliaryDto) {
        Boolean employee = employeeRespo.findByDocumentNumber(auxiliaryDto.getDocumentNumber()).isPresent();
        if (!employee) {
            throw new RuntimeException("El documento "+ auxiliaryDto.getDocumentNumber()+" ya se encuentra vinculado a un empleado");
        }
        Auxiliary entity = auxMapper.toEntity(auxiliaryDto);
        entity.setRegistrationDate(LocalDate.now());
        entity.setActive(true);
        return auxMapper.toDto(entity);
    }

    @Override
    public AuxiliaryResponseDto findById(Long idEmployee){
        Auxiliary auxiliary = auxRepository.findById(idEmployee)
        .orElseThrow(() -> new EntityNotFoundException("No se encontro administrativo asociado al Id "+ idEmployee + " en el sistema"));
        return auxMapper.toDto(auxiliary);
    }

    @Override
    public List<AuxiliaryResponseDto> findAllAuxiliary(){
        return auxRepository.findAll()
            .stream()
            .map(auxMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public AuxiliaryResponseDto findAdminByDocumentNumber(String documentNumber){
        Auxiliary auxiliary = auxRepository.findByDocumentNumber(documentNumber)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro veterianio asociado al numero de documento " + documentNumber));
        return auxMapper.toDto(auxiliary);
    }


    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email){
        employeeService.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updatePhoneNumber(Long idEmployee, String phoneNumber){
        employeeService.updateNumberPhone(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType){
        employeeRespo.updateContractType(idEmployee, contractType);
    }

    @Override
    public void delete (Long idEmployee){
        employeeService.delete(idEmployee);        
    }
}