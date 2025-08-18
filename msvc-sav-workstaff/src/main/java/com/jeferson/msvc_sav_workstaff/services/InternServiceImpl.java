package com.jeferson.msvc_sav_workstaff.services;

import java.time.LocalDate;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.jeferson.msvc_sav_workstaff.dto.InternRequestDto;
import com.jeferson.msvc_sav_workstaff.mapper.InternMapper;
import com.jeferson.msvc_sav_workstaff.models.Intern;
import com.jeferson.msvc_sav_workstaff.repositories.EmployeeRepository;
import com.jeferson.msvc_sav_workstaff.repositories.InternRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InternServiceImpl implements InternService {

    private final InternRepository intRepository;
    private final InternMapper intMapper;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepo;

    public InternServiceImpl(InternRepository intRepository, InternMapper intMapper,
            EmployeeService employeeService, EmployeeRepository employeeRepo) {
        this.intRepository = intRepository;
        this.intMapper = intMapper;
        this.employeeService = employeeService;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public InternRequestDto saveIntern(InternRequestDto internDto) {
        if (employeeRepo.findByDocumentNumber(internDto.getDocumentNumber()).isPresent()) {
            throw new RuntimeException("El documento "+ internDto.getDocumentNumber()+" ya se encuentra vinculado a un empleado");
        }
        Intern entity = intMapper.toEntity(internDto);
        entity.setRegistrationDate(LocalDate.now());
        entity.setActive(true);
        return intMapper.toDto(intRepository.save(entity));
    }

    @Override
    public InternRequestDto findById(Long idEmployee){
        Intern intern = intRepository.findById(idEmployee)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro pasante asociada al id "+ idEmployee));
        return intMapper.toDto(intern);
    }

    @Override
    public InternRequestDto findAdminByDocumentNumber(String documentNumber){
        Intern intern = intRepository.findByDocumentNumber(documentNumber)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro veterianio asociado al numero de documento " + documentNumber));
        return intMapper.toDto(intern);
    }

    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email){
        employeeService.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(Long idEmployee, String phoneNumber){
        employeeService.updateNumberPhone(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType){
        employeeService.updateContractType(idEmployee, contractType);
    }

    @Override
    public void delete (Long idEmployee){
        employeeService.delete(idEmployee);
    }

}