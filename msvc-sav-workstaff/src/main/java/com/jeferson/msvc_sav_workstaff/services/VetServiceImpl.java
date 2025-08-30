package com.jeferson.msvc_sav_workstaff.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.jeferson.msvc_sav_workstaff.dto.VetRequestDto;
import com.jeferson.msvc_sav_workstaff.dto.VetResponseDto;
import com.jeferson.msvc_sav_workstaff.mapper.VetMapper;
import com.jeferson.msvc_sav_workstaff.models.Vet;
import com.jeferson.msvc_sav_workstaff.models.WorkArea;
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
    public VetResponseDto saveVet(VetRequestDto vetDto) {
        if (employeeRepo.findByDocumentNumber(vetDto.getDocumentNumber()).isPresent()) {
            throw new RuntimeException("El documento "+ vetDto.getDocumentNumber()+" ya se encuentra vinculado a un empleado");
        }
        Vet entity = vetMapper.toEntity(vetDto);
        if (vetRepository.existsByProfessionalCard(entity.getProfessionalCard())) {
            throw new IllegalArgumentException("La tarjeta profesional "+ entity.getProfessionalCard() +" ya se encuentra asociado a un veterinario");
        }
        entity.setRegistrationDate(LocalDate.now());
        entity.setActive(true);
        Vet saved = vetRepository.save(entity);
        return vetMapper.toDto(saved);
    }

    @Override
    public List<VetResponseDto> findAllVet(){
        List<Vet> vets = vetRepository.findAll();
        if (vets.isEmpty()) {
            throw new EntityNotFoundException("No se encontro empleados al cargo de veterinarios");
        }
        return vets.stream()
            .map(vetMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public VetResponseDto findById(Long idEmployee){
        Vet vet = vetRepository.findById(idEmployee)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro veterinario asociado al id "+ idEmployee));
        return vetMapper.toDto(vet);
    }

    @Override
    public VetResponseDto findAdminByDocumentNumber(String documentNumber){
        Vet vet = vetRepository.findByDocumentNumber(documentNumber)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro veterianio asociado al numero de documento " + documentNumber));
        return vetMapper.toDto(vet);
    }

    @Override
    @Transactional
    public void updateEmail (Long idEmployee, String email){
        Vet vet = validateInfo(idEmployee);
        if (vet.getEmail().equals(email)) {
            throw new IllegalArgumentException("El email "+ email+ " ya se encuentra asociado al veterinario "+ idEmployee);
        }
        employeeService.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(Long idEmployee, String phoneNumber){
        Vet vet = validateInfo(idEmployee);
        if (vet.getPhoneNumber().equals(phoneNumber)) {
            throw new IllegalArgumentException("El telefono "+ phoneNumber + " ya se encuentra asociado al veterinario "+ idEmployee);
        }
        employeeService.updateNumberPhone(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType){
        Vet vet = validateInfo(idEmployee);
        if (vet.getContractType().equals(contractType)) {
            throw new IllegalArgumentException("El contrato "+ contractType + " ya se encuentra asociado al veterinario "+ idEmployee);
        }        
        employeeService.updateContractType(idEmployee, contractType);
    }

    @Override
    @Transactional
    public void updateWorkArea(Long idEmployee, WorkArea workArea){
        Vet vet = validateInfo(idEmployee);
        if (vet.getWorkArea().equals(workArea)) {
            throw new IllegalArgumentException("El area de trabajo "+ workArea + " ya se encuentra asociado al veterinario "+ idEmployee);
        }
        employeeService.updateWorkArea(idEmployee, workArea);
    }

    @Override
    public void delete(Long idEmployee){
        Vet vet = validateInfo(idEmployee);
        vet.setActive(false);
        employeeRepo.save(vet);
    }

    private Vet validateInfo(Long idEmployee) {
        Vet vet = vetRepository.findById(idEmployee)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro veterinario asociado al id " + idEmployee));
        if (!vet.getActive()) {
            throw new IllegalArgumentException("El empleado " + idEmployee + " se encuentra desactivado del sistema");
        }
        return vet;
    }

}