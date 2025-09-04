package com.jeferson.msvc_sav_workstaff.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.jeferson.msvc_sav_workstaff.dto.InternResponseDto;
import com.jeferson.msvc_sav_workstaff.dto.InternRequestDto;
import com.jeferson.msvc_sav_workstaff.mapper.InternMapper;
import com.jeferson.msvc_sav_workstaff.models.Intern;
import com.jeferson.msvc_sav_workstaff.models.InternRoles;
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
    public InternResponseDto saveIntern(InternRequestDto internDto) {
        if (employeeRepo.findByDocumentNumber(internDto.getDocumentNumber()).isPresent()) {
            throw new RuntimeException(
                    "El documento " + internDto.getDocumentNumber() + " ya se encuentra vinculado a un empleado");
        }
        Intern entity = intMapper.toEntity(internDto);
        entity.setRegistrationDate(LocalDate.now());
        entity.setActive(true);
        Intern saved = intRepository.save(entity);
        return intMapper.toDto(saved);
    }

    @Override
    public List<InternResponseDto> findAllInter() {
        List<Intern> inters = intRepository.findAllActiveInterns();
        if (inters.isEmpty()) {
            throw new EntityNotFoundException("No se encuentran empleados vinculados al cardo de pasantes");
        }
        return inters.stream()
                .map(intMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InternResponseDto findById(Long idEmployee) {
        Intern intern = validateInfo(idEmployee);
        return intMapper.toDto(intern);
    }

    @Override
    public InternResponseDto findAdminByDocumentNumber(String documentNumber) {
        Intern intern = intRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro veterianio asociado al numero de documento " + documentNumber));
        if (!intern.getActive()) {
            throw new IllegalArgumentException(
                    "El Pasante asociado al documento " + documentNumber + " se encuentra desahabilitado del sistema");
        }
        return intMapper.toDto(intern);
    }

    @Override
    @Transactional
    public void updateEmail(Long idEmployee, String email) {
        Intern intern = validateInfo(idEmployee);
        if (intern.getEmail().equals(email)) {
            throw new IllegalArgumentException(
                    "El email " + email + " ya se encuentra asociado al pasante " + idEmployee);
        }
        employeeService.updateEmail(idEmployee, email);
    }

    @Override
    @Transactional
    public void updateNumberPhone(Long idEmployee, String phoneNumber) {
        Intern intern = validateInfo(idEmployee);
        if (intern.getPhoneNumber().equals(phoneNumber)) {
            throw new IllegalArgumentException(
                    "El telefono " + phoneNumber + " ya se encuentra asociado al pasante " + idEmployee);
        }
        employeeService.updateNumberPhone(idEmployee, phoneNumber);
    }

    @Override
    @Transactional
    public void updateContractType(Long idEmployee, ContractType contractType) {
        Intern intern = validateInfo(idEmployee);
        if (intern.getContractType().equals(contractType)) {
            throw new IllegalArgumentException(
                    "El contrato " + contractType + " ya se encuentra asociado al pasante " + idEmployee);
        }
        employeeService.updateContractType(idEmployee, contractType);
    }

    @Override
    @Transactional
    public void updateRoles(Long idEmployee, InternRoles internRoles) {
        Intern intern = validateInfo(idEmployee);
        if (intern.getInternRoles().equals(internRoles)) {
            throw new IllegalArgumentException(
                    "El area de trabajo " + internRoles + " ya se encuentra asociado al pasante " + idEmployee);
        }
        intRepository.updateRole(idEmployee, internRoles);
    }

    @Override
    public void delete(Long idEmployee) {
        Intern intern = validateInfo(idEmployee);
        intern.setActive(false);
        employeeRepo.save(intern);
    }

    private Intern validateInfo(Long idEmployee) {
        Intern intern = intRepository.findById(idEmployee)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro pasante asociado al id " + idEmployee));
        if (!intern.getActive()) {
            throw new IllegalArgumentException("El pasante " + idEmployee + " se encuentra deshabilitado del sistema");
        }
        return intern;
    }

}