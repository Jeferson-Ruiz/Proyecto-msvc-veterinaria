package com.jeferson.msvc_sav_workstaff.services;

import java.time.LocalDate;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.jeferson.msvc_sav_workstaff.dto.VetRequestDto;
import com.jeferson.msvc_sav_workstaff.mapper.VetMapper;
import com.jeferson.msvc_sav_workstaff.models.Vet;
import com.jeferson.msvc_sav_workstaff.repositories.EmployeeRepository;
import com.jeferson.msvc_sav_workstaff.repositories.VetRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VetServiceImpl implements VetService {

    private final EmployeeRepository employeeRepo;
    private final VetRepository vetRepository;
    private final EmployeeService employeeService;
    private final VetMapper vetMapper;

    public VetServiceImpl(VetRepository vetRepository, EmployeeService empRespository, VetMapper vetMapper, EmployeeRepository employeeRepo) {
        this.vetRepository = vetRepository;
        this.employeeService = empRespository;
        this.vetMapper = vetMapper;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public VetRequestDto saveVet(VetRequestDto vetDto) {
        if (employeeRepo.findByDocumentNumber(vetDto.getDocumentNumber()).isPresent()) {
            throw new RuntimeException("El documento "+ vetDto.getDocumentNumber()+" ya se encuentra vinculado a un empleado");
        }
        Vet entity = vetMapper.toEntity(vetDto);
        entity.setRegistrationDate(LocalDate.now());
        entity.setActive(true);
        return vetMapper.toDto(vetRepository.save(entity));
    }

    @Override
    public VetRequestDto findById(Long idEmployee){
        Vet vet = vetRepository.findById(idEmployee)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro veterinario asociado al id "+ idEmployee));
        return vetMapper.toDto(vet);
    }

    @Override
    public VetRequestDto findAdminByDocumentNumber(String documentNumber){
        Vet vet = vetRepository.findByDocumentNumber(documentNumber)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro veterianio asociado al numero de documento " + documentNumber));
        return vetMapper.toDto(vet);
    }

    @Override
    @Transactional
    public void updateEmail (Long idEmployee, String email){
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
    public void delete(Long idEmployee){
        employeeService.delete(idEmployee);
    }

}