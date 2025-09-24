package com.jeferson.msvc_sav_workstaff.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.jeferson.msvc_sav_workstaff.models.ActionInformation;
import com.jeferson.msvc_sav_workstaff.models.ContractType;
import com.jeferson.msvc_sav_workstaff.models.EmployeeStatus;
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
        entity.setStatus(EmployeeStatus.PROCESS);
        Intern saved = intRepository.save(entity);
        return intMapper.toDto(saved);
    }

    @Override
    public List<InternResponseDto> findAllByRole(InternRoles internRole, EmployeeStatus status){
        employeeService.validateStatus(status);
        List<Intern> interns = intRepository.findAllByRole(internRole, status);
        if (interns.isEmpty()) {
            throw new EntityNotFoundException("No se encontro registro de " + internRole + " asociados al estado de "+ status);
        }
        return mapInternsByStatus(status, interns);
    }

    @Override
    public List<InternResponseDto> findAllByStatus(EmployeeStatus status){
        employeeService.validateStatus(status);
        List<Intern> interns = intRepository.findAllByStatus(status);
        if (interns.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron pasante asociados al estado laboral de "+ status );
        }
        return mapInternsByStatus(status, interns);
    }

    @Override
    public InternResponseDto findById(Long idEmployee) {
        Intern intern = intRepository.findById(idEmployee)
            .orElseThrow(() -> new EntityNotFoundException("No se encontro pasante asociado al id "+ idEmployee));
        return mapInternByStatus(intern);
    }

    @Override
    public InternResponseDto findAdminByDocumentNumber(String documentNumber) {
        Intern intern = intRepository.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontro veterianio asociado al numero de documento " + documentNumber));
        return mapInternByStatus(intern);
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
    public void delete(Long idEmployee, String deleteBy, String reason) {
        Intern intern = validateInfo(idEmployee);
        intern.setStatus(EmployeeStatus.DELETED);
        applyStatusChange(intern, deleteBy, reason);
    }

    @Override
    public void suspended(Long idEmployee, String deleteBy, String reason) {
        Intern intern = validateInfo(idEmployee);
        if (intern.getStatus() == EmployeeStatus.SUSPENDED) {
            throw new IllegalArgumentException("El practicante ya se encuentra suspendido del sistema");
        }
        intern.setStatus(EmployeeStatus.SUSPENDED);
        applyStatusChange(intern, deleteBy, reason);
    }


    //helper
    private Intern validateInfo(Long idEmployee) {
        Intern intern = intRepository.findById(idEmployee)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro pasante asociado al id " + idEmployee));
        if (intern.getStatus() == EmployeeStatus.DELETED) {
            throw new IllegalArgumentException("El pasante " + idEmployee + " se encuentra deshabilitado permanentemente del sistema");
        }
        return intern;
    }

    private List<InternResponseDto> mapInternsByStatus(EmployeeStatus status, List<Intern> interns){
        return switch (status) {
            case ACTIVE, PROCESS -> interns.stream()
                .map(intMapper::toDto) 
                .toList();
            case DELETED, SUSPENDED -> interns.stream()
                .map(intMapper::toDisabledDto)
                .map(dto -> (InternResponseDto) dto).toList();                
        };
    }

    private InternResponseDto mapInternByStatus(Intern intern){
        EmployeeStatus status = intern.getStatus();
        return switch (status) {
            case ACTIVE, PROCESS -> intMapper.toDto(intern);
            case DELETED, SUSPENDED -> intMapper.toDisabledDto(intern);
        };
    }

    private void applyStatusChange(Intern intern, String deleteBy, String reason) {
        ActionInformation actionInf = new ActionInformation();
        actionInf.setDeletedAt(LocalDateTime.now());
        actionInf.setDeletedBy(deleteBy);
        actionInf.setReason(reason);
        actionInf.setEmployee(intern);
        intern.getActionInformations().add(actionInf);
        intRepository.save(intern);
    }

}